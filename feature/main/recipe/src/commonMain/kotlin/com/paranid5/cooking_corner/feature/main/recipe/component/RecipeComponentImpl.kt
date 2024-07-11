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
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent.BackResult
import com.paranid5.cooking_corner.feature.main.recipe.utils.fromResponse
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.utils.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class RecipeComponentImpl(
    componentContext: ComponentContext,
    recipeId: Long,
    private val recipeRepository: RecipeRepository,
    private val globalEventRepository: GlobalEventRepository,
    private val onBack: (BackResult) -> Unit,
) : RecipeComponent, ComponentContext by componentContext {
    private val componentState = getComponentState(defaultState = RecipeState())
    private val _stateFlow = MutableStateFlow(componentState.value)
    override val stateFlow = _stateFlow.asStateFlow()

    init {
        doOnResume { loadRecipe(recipeId) }
    }

    override fun onUiIntent(intent: RecipeUiIntent) {
        when (intent) {
            is RecipeUiIntent.Back -> onBack(BackResult.Dismiss)

            is RecipeUiIntent.ChangeKebabMenuVisibility ->
                changeKebabMenuVisibility(isVisible = intent.isVisible)

            is RecipeUiIntent.Edit -> onBack(BackResult.Edit)

            is RecipeUiIntent.Publish -> publishRecipe()

            is RecipeUiIntent.Delete -> deleteRecipe()
        }
    }

    private fun changeKebabMenuVisibility(isVisible: Boolean) =
        _stateFlow.updateState { copy(isKebabMenuVisible = isVisible) }

    private fun publishRecipe() {
        // TODO: publish request
    }

    private fun deleteRecipe() {
        // TODO: delete recipe
    }

    private fun loadRecipe(recipeId: Long) = componentScope.launch {
        handleLoadRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.getRecipeById(recipeId)
            }
        ) { recipeResponse ->
            _stateFlow.updateState {
                copy(recipeUiState = RecipeDetailedUiState.fromResponse(recipeResponse).toUiState())
            }
        }
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
            status.value.isForbidden ->
                globalEventRepository.sendEvent(GlobalEvent.LogOut(Reason.ERROR))

            else -> _stateFlow.updateState { copy(recipeUiState = UiState.Error()) }
        }

        is Either.Right -> onSuccess(status.value)
    }

    class Factory(
        private val recipeRepository: RecipeRepository,
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
            globalEventRepository = globalEventRepository,
            onBack = onBack,
        )
    }
}
