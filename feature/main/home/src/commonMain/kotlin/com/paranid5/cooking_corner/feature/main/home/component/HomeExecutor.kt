package com.paranid5.cooking_corner.feature.main.home.component

import arrow.core.Either
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.HttpStatusCode
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.category.CategoryRepository
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
import com.paranid5.cooking_corner.ui.entity.CategoryUiState
import com.paranid5.cooking_corner.feature.main.recipe.utils.fromResponse
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.doNothing
import com.paranid5.cooking_corner.utils.mapToImmutableList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class HomeExecutor(
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository,
    private val globalEventRepository: GlobalEventRepository,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.LoadMyRecipes -> loadMyRecipes()

            is UiIntent.ShowRecipe -> publish(Label.ShowRecipe(recipeId = intent.recipeId))

            is UiIntent.UpdateSearchText -> dispatch(Msg.UpdateSearchText(intent.text))

            is UiIntent.SelectCategory -> dispatch(Msg.SelectCategory(intent.index))

            is UiIntent.AddRecipe -> publish(Label.ShowAddRecipe)

            is UiIntent.GenerateRecipe -> publish(Label.ShowGenerateRecipe)

            is UiIntent.OrderClick -> dispatch(Msg.UpdateOrder)

            is UiIntent.ShowFavourites -> doNothing // TODO: Show favourites

            is UiIntent.LikeClick -> addToFavourites(
                recipeId = intent.recipeId,
                unhandledErrorMessage = intent.unhandledErrorMessage,
            )

            is UiIntent.DislikeClick -> removeFromFavourites(
                recipeId = intent.recipeId,
                unhandledErrorMessage = intent.unhandledErrorMessage,
            )
        }
    }

    override fun executeAction(action: Unit) = loadCategories()

    // -------------------- UI Intent handling --------------------

    private fun loadMyRecipes() {
        dispatch(Msg.UpdateRecipesUiState(UiState.Loading))

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

    private fun loadCategories() {
        dispatch(Msg.UpdateCategoriesUiState(UiState.Loading))

        scope.launch {
            handleCategoriesApiResult(
                result = withContext(AppDispatchers.Data) { categoryRepository.getAll() }
            )
        }
    }

    private fun addToFavourites(
        recipeId: Long,
        unhandledErrorMessage: String,
    ) = scope.launch {
        handleModifyRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.addToFavourites(recipeId = recipeId)
            },
            unhandledErrorMessage = unhandledErrorMessage,
            onSuccess = ::loadMyRecipes,
        )
    }

    private fun removeFromFavourites(
        recipeId: Long,
        unhandledErrorMessage: String,
    ) = scope.launch {
        handleModifyRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.removeFromFavourites(recipeId = recipeId)
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
            dispatch(Msg.UpdateRecipesUiState(result.value.toUiState()))
        }

        is Either.Right -> handleRecipesStatus(result.value)
    }

    private suspend inline fun handleRecipesStatus(
        status: Either<HttpStatusCode, List<RecipeResponse>>,
    ) = when (status) {
        is Either.Left -> when {
            status.value.isForbidden ->
                globalEventRepository.sendEvent(GlobalEvent.LogOut(Reason.ERROR))

            else -> dispatch(Msg.UpdateRecipesUiState(UiState.Error()))
        }

        is Either.Right -> dispatch(
            Msg.UpdateRecipesUiState(
                withContext(AppDispatchers.Eval) {
                    status.value
                        .mapToImmutableList(RecipeUiState.Companion::fromResponse)
                        .let(::SerializableImmutableList)
                        .toUiState()
                }
            )
        )
    }

    private suspend inline fun handleCategoriesApiResult(
        result: ApiResultWithCode<List<String>>,
    ) = when (result) {
        is Either.Left -> {
            result.value.printStackTrace()
            dispatch(Msg.UpdateCategoriesUiState(result.value.toUiState()))
        }

        is Either.Right -> handleCategoriesStatus(result.value)
    }

    private suspend inline fun handleCategoriesStatus(
        status: Either<HttpStatusCode, List<String>>,
    ) = when (status) {
        is Either.Left -> dispatch(Msg.UpdateCategoriesUiState(UiState.Error()))

        is Either.Right -> dispatch(
            Msg.UpdateCategoriesUiState(
                withContext(AppDispatchers.Eval) {
                    status.value
                        .mapToImmutableList(::CategoryUiState)
                        .let(::SerializableImmutableList)
                        .toUiState()
                }
            )
        )
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
