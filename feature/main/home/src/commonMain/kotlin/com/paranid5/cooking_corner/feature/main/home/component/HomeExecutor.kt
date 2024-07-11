package com.paranid5.cooking_corner.feature.main.home.component

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
            is UiIntent.LoadMyRecipes -> loadMyRecipes()

            is UiIntent.ShowRecipe -> publish(Label.ShowRecipe(intent.recipeUiState))

            is UiIntent.UpdateSearchText -> dispatch(Msg.UpdateSearchText(intent.text))

            is UiIntent.SelectCategory -> dispatch(Msg.SelectCategory(intent.index))

            is UiIntent.AddRecipe -> publish(Label.ShowAddRecipe)

            is UiIntent.GenerateRecipe -> publish(Label.ShowGenerateRecipe)

            is UiIntent.OrderClick -> dispatch(Msg.UpdateOrder)

            is UiIntent.ShowFavourites -> doNothing // TODO: Show favourites

            is UiIntent.LikeClick -> addToFavourites(
                recipeUiState = intent.recipeUiState,
                unhandledErrorMessage = intent.unhandledErrorMessage,
            )

            is UiIntent.DislikeClick -> removeFromFavourites(
                recipeUiState = intent.recipeUiState,
                unhandledErrorMessage = intent.unhandledErrorMessage,
            )
        }
    }

    // -------------------- UI Intent handling --------------------

    private fun loadMyRecipes() {
        dispatch(Msg.UpdateUiState(UiState.Loading))

        scope.launch {
            handleRecipesApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getMyRecipes(
                        categoryName = state().selectedCategoryTitle,
                        ascendingOrder = state().isAscendingOrder,
                    )
                }
            )
        }
    }

    private fun addToFavourites(
        recipeUiState: RecipeUiState,
        unhandledErrorMessage: String,
    ) = scope.launch {
        handleModifyRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.addToFavourites(recipeId = recipeUiState.id)
            },
            unhandledErrorMessage = unhandledErrorMessage,
            onSuccess = ::loadMyRecipes,
        )
    }

    private fun removeFromFavourites(
        recipeUiState: RecipeUiState,
        unhandledErrorMessage: String,
    ) = scope.launch {
        handleModifyRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.removeFromFavourites(recipeId = recipeUiState.id)
            },
            unhandledErrorMessage = unhandledErrorMessage,
            onSuccess = ::loadMyRecipes,
        )
    }

    // -------------------- API results handling --------------------

    private suspend inline fun handleRecipesApiResult(
        result: ApiResultWithCode<List<RecipeResponse>>,
    ) = when (result) {
        is Either.Left -> {
            result.value.printStackTrace()
            dispatch(Msg.UpdateUiState(result.value.toUiState()))
        }

        is Either.Right -> handleRecipesStatus(result.value)
    }

    private suspend inline fun handleRecipesStatus(
        status: Either<HttpStatusCode, List<RecipeResponse>>,
    ) = when (status) {
        is Either.Left -> when {
            status.value.isForbidden ->
                globalEventRepository.sendEvent(GlobalEvent.LogOut(Reason.ERROR))

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
