package com.paranid5.cooking_corner.feature.main.recipe.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import com.paranid5.cooking_corner.component.componentScope
import com.paranid5.cooking_corner.component.getComponentState
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.HttpStatusCode
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendLogOut
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent.BackResult
import com.paranid5.cooking_corner.ui.entity.mappers.fromResponse
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.utils.api.handleApiResult
import com.paranid5.cooking_corner.utils.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val RECIPE_CANNOT_BE_DELETED = 400

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
        doOnResume { loadRecipe(recipeId = recipeId) }
    }

    override fun onUiIntent(intent: RecipeUiIntent) {
        when (intent) {
            is RecipeUiIntent.Back -> onBack(BackResult.Dismiss)

            is RecipeUiIntent.Refresh -> stateFlow.value.recipeId?.let {
                loadRecipe(recipeId = it)
            }

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
                    unhandledErrorSnackbar = intent.unhandledErrorSnackbar,
                    recipeCannotBeDeletedSnackbar = intent.recipeCannotBeDeletedSnackbar,
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

    private fun loadRecipe(recipeId: Long) {
        loadUsername()
        loadRecipeDetails(recipeId)
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
            unhandledErrorSnackbar = errorSnackbar,
            onApiError = { showSnackbar(errorSnackbar) },
        ) {
            globalEventRepository.sendSnackbar(successSnackbar)
            loadRecipeDetails(recipeId = recipeId)
        }
    }

    private fun deleteRecipe(
        recipeId: Long,
        unhandledErrorSnackbar: SnackbarMessage,
        recipeCannotBeDeletedSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) = componentScope.launch {
        handleModifyRecipeApiResult(
            result = recipeRepository.removeFromMyRecipes(recipeId = recipeId),
            unhandledErrorSnackbar = unhandledErrorSnackbar,
            onApiError = { (status) ->
                showSnackbar(
                    when (status) {
                        RECIPE_CANNOT_BE_DELETED -> recipeCannotBeDeletedSnackbar
                        else -> unhandledErrorSnackbar
                    }
                )
            }
        ) {
            showSnackbar(successSnackbar)
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
            unhandledErrorSnackbar = errorSnackbar,
            onApiError = { showSnackbar(errorSnackbar) },
            onSuccess = { loadRecipeDetails(recipeId = recipeId) },
        )
    }

    private fun loadRecipeDetails(recipeId: Long) {
        _stateFlow.updateState { copy(recipeUiState = UiState.Loading) }

        componentScope.launch {
            handleApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getRecipeById(recipeId = recipeId)
                },
                onUnhandledError = {
                    _stateFlow.updateState { copy(recipeUiState = UiState.Error()) }
                },
                onErrorStatusCode = { status ->
                    when {
                        status.isForbidden -> logOutWithError()
                        else -> _stateFlow.updateState { copy(recipeUiState = UiState.Error()) }
                    }
                },
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
            authRepository.loginFlow.firstOrNull()
        }

        _stateFlow.updateState { copy(username = user) }
    }

    private suspend inline fun handleModifyRecipeApiResult(
        result: ApiResultWithCode<Unit>,
        unhandledErrorSnackbar: SnackbarMessage,
        onApiError: (HttpStatusCode) -> Unit,
        onSuccess: () -> Unit,
    ) = handleApiResult(
        result = result,
        onUnhandledError = { showSnackbar(unhandledErrorSnackbar) },
        onErrorStatusCode = { status ->
            when {
                status.isForbidden -> logOutWithError()
                else -> onApiError(status)
            }
        },
        onSuccess = { onSuccess() },
    )

    private suspend inline fun showSnackbar(snackbarMessage: SnackbarMessage) =
        globalEventRepository.sendSnackbar(snackbarMessage)

    private suspend inline fun logOutWithError() =
        globalEventRepository.sendLogOut(Reason.ERROR)

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
