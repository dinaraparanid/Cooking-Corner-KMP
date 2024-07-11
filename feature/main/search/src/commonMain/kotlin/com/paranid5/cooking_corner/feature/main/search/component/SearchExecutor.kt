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
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.feature.main.recipe.utils.fromResponse
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.feature.main.search.component.SearchStoreProvider.Msg
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
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
            is UiIntent.LoadRecipes -> loadRecipes()

            is UiIntent.AddToMyRecipesClick -> addToMyRecipes(
                recipeUiState = intent.recipeUiState,
                unhandledErrorMessage = intent.unhandledErrorMessage,
            )

            is UiIntent.RemoveFromMyRecipesClick -> removeFromMyRecipes(
                recipeUiState = intent.recipeUiState,
                unhandledErrorMessage = intent.unhandledErrorMessage,
            )

            is UiIntent.SearchRecipes -> doNothing // TODO: Search recipes by name

            is UiIntent.ShowRecipe -> publish(Label.ShowRecipe(intent.recipeId))

            is UiIntent.UpdateSearchText -> dispatch(Msg.UpdateSearchText(intent.text))
        }
    }

    // -------------------- UI Intent handling --------------------

    private fun loadRecipes() {
        dispatch(Msg.UpdateUiState(UiState.Loading))

        scope.launch {
            handleRecipesApiResult(
                onSuccess = {
                    dispatch(Msg.UpdateBestRatedRecipes(recipes = SerializableImmutableList(it)))
                },
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getBestRatedRecipes()
                }
            )
        }

        scope.launch {
            handleRecipesApiResult(
                onSuccess = {
                    dispatch(Msg.UpdateRecentRecipes(recipes = SerializableImmutableList(it)))
                },
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getRecentRecipes()
                }
            )
        }
    }

    private fun addToMyRecipes(
        recipeUiState: RecipeUiState,
        unhandledErrorMessage: String,
    ) = scope.launch {
        handleModifyRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.addToMyRecipes(recipeId = recipeUiState.id)
            },
            unhandledErrorMessage = unhandledErrorMessage,
            onSuccess = ::loadRecipes,
        )
    }

    private fun removeFromMyRecipes(
        recipeUiState: RecipeUiState,
        unhandledErrorMessage: String,
    ) = scope.launch {
        handleModifyRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.removeFromMyRecipes(recipeId = recipeUiState.id)
            },
            unhandledErrorMessage = unhandledErrorMessage,
            onSuccess = ::loadRecipes,
        )
    }

    // -------------------- API results handling --------------------

    private suspend inline fun handleRecipesApiResult(
        result: ApiResultWithCode<List<RecipeResponse>>,
        onSuccess: (recipes: ImmutableList<RecipeUiState>) -> Unit,
    ) = when (result) {
        is Either.Left -> {
            result.value.printStackTrace()
            dispatch(Msg.UpdateUiState(result.value.toUiState()))
        }

        is Either.Right -> handleRecipesStatus(
            status = result.value,
            onSuccess = onSuccess,
        )
    }

    private suspend inline fun handleRecipesStatus(
        status: Either<HttpStatusCode, List<RecipeResponse>>,
        onSuccess: (recipes: ImmutableList<RecipeUiState>) -> Unit,
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
            onSuccess(
                withContext(AppDispatchers.Eval) {
                    status.value
                        .map(RecipeUiState.Companion::fromResponse)
                        .toImmutableList()
                }
            )

            dispatch(Msg.UpdateUiState(UiState.Success))
        }
    }

    private suspend inline fun handleModifyRecipeApiResult(
        result: ApiResultWithCode<Unit>,
        unhandledErrorMessage: String,
        onSuccess: () -> Unit,
    ) = when (result) {
        is Either.Left -> {
            result.value.printStackTrace()
            globalEventRepository.sendEvent(
                GlobalEvent.ShowSnackbar(
                    SnackbarMessage(
                        message = unhandledErrorMessage,
                        snackbarType = SnackbarType.NEGATIVE,
                        withDismissAction = true,
                    )
                )
            )
        }

        is Either.Right -> handleModifyRecipeStatus(
            status = result.value,
            unhandledErrorMessage = unhandledErrorMessage,
            onSuccess = onSuccess,
        )
    }

    private suspend inline fun handleModifyRecipeStatus(
        status: Either<HttpStatusCode, Unit>,
        unhandledErrorMessage: String,
        onSuccess: () -> Unit,
    ) = when (status) {
        is Either.Left -> when {
            status.value.isForbidden ->
                globalEventRepository.sendEvent(GlobalEvent.LogOut(Reason.ERROR))

            else -> globalEventRepository.sendEvent(
                GlobalEvent.ShowSnackbar(
                    SnackbarMessage(
                        message = unhandledErrorMessage,
                        snackbarType = SnackbarType.NEGATIVE,
                        withDismissAction = true,
                    )
                )
            )
        }

        is Either.Right -> onSuccess()
    }
}