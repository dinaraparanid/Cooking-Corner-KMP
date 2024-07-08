package com.paranid5.cooking_corner.feature.main.home.component

import arrow.core.Either
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.HttpStatusCode
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.global_event.Event
import com.paranid5.cooking_corner.domain.global_event.Event.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.recipe.entity.RecipeResponse
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.home.component.HomeStoreProvider.Msg
import com.paranid5.cooking_corner.feature.main.recipe.utils.fromResponse
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.utils.doNothing
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class HomeExecutor(
    private val recipeRepository: RecipeRepository,
    private val globalEventRepository: GlobalEventRepository,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.ShowRecipe -> publish(Label.ShowRecipe(intent.recipeUiState))
            is UiIntent.UpdateSearchText -> dispatch(Msg.UpdateSearchText(intent.text))
            is UiIntent.SelectCategory -> dispatch(Msg.SelectCategory(intent.index))
            is UiIntent.AddRecipe -> publish(Label.ShowAddRecipe)
            is UiIntent.GenerateRecipe -> publish(Label.ShowGenerateRecipe)
            is UiIntent.DescendingFilterClick -> doNothing // TODO: Descending filter
            is UiIntent.ShowFavourites -> doNothing // TODO: Show favourites
            is UiIntent.LikeClick -> doNothing // TODO: Like click
        }
    }

    override fun executeAction(action: Unit) {
        dispatch(Msg.UpdateUiState(UiState.Loading))

        scope.launch {
            handleApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getMyRecipes()
                }
            )
        }
    }

    private suspend inline fun handleStatus(status: Either<HttpStatusCode, List<RecipeResponse>>) =
        when (status) {
            is Either.Left -> when {
                status.value.isForbidden ->
                    globalEventRepository.sendEvent(Event.LogOut(Reason.ERROR))

                else -> dispatch(Msg.UpdateUiState(UiState.Error()))
            }

            is Either.Right -> {
                dispatch(
                    Msg.UpdateRecipes(
                        withContext(AppDispatchers.Eval) {
                            status.value
                                .map(RecipeUiState.Companion::fromResponse)
                                .toImmutableList()
                        }
                    )
                )

                dispatch(Msg.UpdateUiState(UiState.Success))
            }
        }

    private suspend inline fun handleApiResult(result: ApiResultWithCode<List<RecipeResponse>>) =
        when (result) {
            is Either.Left -> {
                result.value.printStackTrace()
                dispatch(Msg.UpdateUiState(result.value.toUiState()))
            }

            is Either.Right -> handleStatus(result.value)
        }
}
