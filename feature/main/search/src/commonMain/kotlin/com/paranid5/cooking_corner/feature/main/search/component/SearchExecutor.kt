package com.paranid5.cooking_corner.feature.main.search.component

import arrow.core.Either
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.HttpStatusCode
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.recipe.entity.RecipeResponse
import com.paranid5.cooking_corner.feature.main.recipe.utils.fromResponse
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.feature.main.search.component.SearchStoreProvider.Msg
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.utils.doNothing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SearchExecutor(
    private val recipeRepository: RecipeRepository,
    private val globalEventRepository: GlobalEventRepository,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.AddToRecipesClick -> doNothing // TODO: Add to recipes
            is UiIntent.ShowRecipe -> publish(Label.ShowRecipe(intent.recipeUiState))
            is UiIntent.UpdateSearchText -> dispatch(Msg.UpdateSearchText(intent.text))
        }
    }

    override fun executeAction(action: Unit) {
        dispatch(Msg.UpdateUiState(UiState.Loading))

        scope.launch {
            handleRecipesApiResult(
                onRecipesReceived = { dispatch(Msg.UpdateBestRatedRecipes(recipes = it)) },
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getBestRatedRecipes()
                }
            )
        }

        scope.launch {
            handleRecipesApiResult(
                onRecipesReceived = { dispatch(Msg.UpdateRecentRecipes(recipes = it)) },
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getMyRecipes()
                }
            )
        }
    }

    private suspend inline fun handleRecipesApiResult(
        result: ApiResultWithCode<List<RecipeResponse>>,
        onRecipesReceived: (recipes: ImmutableList<RecipeUiState>) -> Unit,
    ) = when (result) {
        is Either.Left -> {
            result.value.printStackTrace()
            dispatch(Msg.UpdateUiState(result.value.toUiState()))
        }

        is Either.Right -> handleRecipesStatus(
            status = result.value,
            onRecipesReceived = onRecipesReceived,
        )
    }

    private suspend inline fun handleRecipesStatus(
        status: Either<HttpStatusCode, List<RecipeResponse>>,
        onRecipesReceived: (recipes: ImmutableList<RecipeUiState>) -> Unit,
    ) = when (status) {
        is Either.Left -> when {
            status.value.isForbidden ->
                globalEventRepository.sendEvent(GlobalEvent.LogOut(Reason.MANUAL))

            else -> {
                println(status.value)
                dispatch(Msg.UpdateUiState(UiState.Error()))
            }
        }

        is Either.Right -> {
            onRecipesReceived(
                withContext(AppDispatchers.Eval) {
                    status.value
                        .map(RecipeUiState.Companion::fromResponse)
                        .toImmutableList()
                }
            )

            dispatch(Msg.UpdateUiState(UiState.Success))
        }
    }
}