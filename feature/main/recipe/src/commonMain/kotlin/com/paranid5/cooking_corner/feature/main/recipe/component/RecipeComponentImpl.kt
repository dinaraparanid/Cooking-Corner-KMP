package com.paranid5.cooking_corner.feature.main.recipe.component

import arrow.core.Either
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import com.paranid5.cooking_corner.component.componentScope
import com.paranid5.cooking_corner.component.getComponentState
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.HttpStatusCode
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendLogOut
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent.BackResult
import com.paranid5.cooking_corner.ui.entity.mappers.fromResponse
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.utils.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class RecipeComponentImpl(
    componentContext: ComponentContext,
    recipeId: Long,
    private val recipeRepository: RecipeRepository,
    private val authRepository: AuthRepository,
    private val globalEventRepository: GlobalEventRepository,
    private val onBack: (BackResult) -> Unit,
) : RecipeComponent, ComponentContext by componentContext {
    private val componentState = getComponentState(defaultState = RecipeState())
    private val _stateFlow = MutableStateFlow(componentState.value)
    override val stateFlow = _stateFlow.asStateFlow()

    init {
        doOnResume {
            loadUsername()
            loadRecipe(recipeId)
        }
    }

    override fun onUiIntent(intent: RecipeUiIntent) {
        when (intent) {
            is RecipeUiIntent.Back -> onBack(BackResult.Dismiss)

            is RecipeUiIntent.ChangeKebabMenuVisibility ->
                changeKebabMenuVisibility(isVisible = intent.isVisible)

            is RecipeUiIntent.Edit -> stateFlow.value.recipeId?.let {
                onBack(BackResult.Edit(recipeId = it))
            }

            is RecipeUiIntent.Publish -> stateFlow.value.recipeId?.let {
                publishRecipe(
                    recipeId = it,
                    successSnackbar = intent.successSnackbar,
                    errorSnackbar = intent.errorSnackbar,
                )
            }

            is RecipeUiIntent.Delete -> stateFlow.value.recipeId?.let {
                deleteRecipe(
                    recipeId = it,
                    successSnackbar = intent.successSnackbar,
                    errorSnackbar = intent.errorSnackbar,
                )
            }

            is RecipeUiIntent.Rate -> stateFlow.value.recipeId?.let {
                rateRecipe(
                    recipeId = it,
                    rating = intent.rating,
                    errorSnackbar = intent.errorSnackbar,
                )
            }
        }
    }

    private fun changeKebabMenuVisibility(isVisible: Boolean) =
        _stateFlow.updateState { copy(isKebabMenuVisible = isVisible) }

    private fun publishRecipe(
        recipeId: Long,
        errorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) = componentScope.launch {
        handleModifyRecipeApiResult(
            result = recipeRepository.publish(recipeId = recipeId),
            errorSnackbar = errorSnackbar,
        ) {
            globalEventRepository.sendSnackbar(successSnackbar)
            loadRecipe(recipeId = recipeId)
        }
    }

    private fun deleteRecipe(
        recipeId: Long,
        errorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) = componentScope.launch {
        handleModifyRecipeApiResult(
            result = recipeRepository.removeFromMyRecipes(recipeId = recipeId),
            errorSnackbar = errorSnackbar,
        ) {
            globalEventRepository.sendSnackbar(successSnackbar)
            onBack(BackResult.Dismiss)
        }
    }

    private fun rateRecipe(
        recipeId: Long,
        rating: Int,
        errorSnackbar: SnackbarMessage,
    ) = componentScope.launch {
        handleModifyRecipeApiResult(
            result = recipeRepository.rate(recipeId = recipeId, rating = rating),
            errorSnackbar = errorSnackbar,
            onSuccess = { loadRecipe(recipeId = recipeId) },
        )
    }

    private fun loadRecipe(recipeId: Long) {
        _stateFlow.updateState { copy(recipeUiState = UiState.Loading) }

        componentScope.launch {
            handleLoadRecipeApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getRecipeById(recipeId = recipeId)
                }
            ) { recipeResponse ->
                _stateFlow.updateState {
                    copy(
                        recipeUiState = RecipeDetailedUiState
                            .fromResponse(recipeResponse)
                            .toUiState()
                    )
                }
            }
        }
    }

    private fun loadUsername() = componentScope.launch {
        val user = withContext(AppDispatchers.Data) {
            authRepository
                .loginFlow
                .firstOrNull()
        }

        _stateFlow.updateState { copy(username = user) }
    }

    private suspend inline fun handleLoadRecipeApiResult(
        result: ApiResultWithCode<RecipeResponse>,
        onSuccess: (RecipeResponse) -> Unit,
    ) = when (result) {
        is Either.Left -> {
            result.value.printStackTrace()
            _stateFlow.updateState { copy(recipeUiState = UiState.Error()) }
        }

        is Either.Right -> handleLoadRecipeStatus(
            status = result.value,
            onSuccess = onSuccess,
        )
    }

    private suspend inline fun handleLoadRecipeStatus(
        status: Either<HttpStatusCode, RecipeResponse>,
        onSuccess: (RecipeResponse) -> Unit,
    ) = when (status) {
        is Either.Left -> when {
            status.value.isForbidden -> globalEventRepository.sendLogOut(Reason.ERROR)
            else -> _stateFlow.updateState { copy(recipeUiState = UiState.Error()) }
        }

        is Either.Right -> onSuccess(status.value)
    }

    private suspend inline fun handleModifyRecipeApiResult(
        result: ApiResultWithCode<Unit>,
        errorSnackbar: SnackbarMessage,
        onSuccess: () -> Unit,
    ) = when (result) {
        is Either.Left -> {
            result.value.printStackTrace()
            globalEventRepository.sendSnackbar(errorSnackbar)
        }

        is Either.Right -> handleModifyRecipeStatus(
            status = result.value,
            errorSnackbar = errorSnackbar,
            onSuccess = onSuccess,
        )
    }

    private suspend inline fun handleModifyRecipeStatus(
        status: Either<HttpStatusCode, Unit>,
        errorSnackbar: SnackbarMessage,
        onSuccess: () -> Unit,
    ) = when (status) {
        is Either.Left -> when {
            status.value.isForbidden -> globalEventRepository.sendLogOut(Reason.ERROR)
            else -> globalEventRepository.sendSnackbar(errorSnackbar)
        }

        is Either.Right -> onSuccess()
    }

    class Factory(
        private val recipeRepository: RecipeRepository,
        private val authRepository: AuthRepository,
        private val globalEventRepository: GlobalEventRepository,
    ) : RecipeComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            recipeId: Long,
            onBack: (BackResult) -> Unit,
        ): RecipeComponent = RecipeComponentImpl(
            componentContext = componentContext,
            recipeId = recipeId,
            recipeRepository = recipeRepository,
            authRepository = authRepository,
            globalEventRepository = globalEventRepository,
            onBack = onBack,
        )
    }
}
