package com.paranid5.cooking_corner.feature.main.search.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.HttpStatusCode
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendLogOut
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.ui.entity.mappers.fromResponse
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.feature.main.search.component.SearchStoreProvider.Msg
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.api.handleApiResult
import com.paranid5.cooking_corner.utils.mapToImmutableList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val RECIPE_CANNOT_BE_DELETED = 400
private const val UPDATE_SEARCH_TEXT_DELAY = 1000L

internal class SearchExecutor(
    private val recipeRepository: RecipeRepository,
    private val globalEventRepository: GlobalEventRepository,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    private var currentUpdateSearchTextTask: Job? = null

    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.LoadRecipes -> loadRecipes()

            is UiIntent.AddToMyRecipesClick -> addToMyRecipes(
                recipeUiState = intent.recipeUiState,
                unhandledErrorSnackbar = intent.unhandledErrorSnackbar,
            )

            is UiIntent.RemoveFromMyRecipesClick -> removeFromMyRecipes(
                recipeUiState = intent.recipeUiState,
                unhandledErrorSnackbar = intent.unhandledErrorSnackbar,
                recipeCannotBeDeletedSnackbar = intent.recipeCannotBeDeletedSnackbar,
            )

            is UiIntent.SearchRecipes -> launchNewSearchTextUpdateQuery(text = state().searchText)

            is UiIntent.ShowRecipe -> publish(Label.ShowRecipe(intent.recipeId))

            is UiIntent.UpdateSearchText -> dispatch(Msg.UpdateSearchText(text = intent.text))

            is UiIntent.CancelSearching -> dispatch(Msg.CancelSearching)
        }
    }

    // -------------------- UI Intent handling --------------------

    private fun loadRecipes() {
        dispatch(Msg.UpdatePreviewUiState(UiState.Loading))

        val loadBestRatedTask = scope.async {
            handleRecipesApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getBestRatedRecipes()
                },
                onUnhandledError = { dispatch(Msg.UpdatePreviewUiState(it.toUiState())) },
                onErrorStatusCode = { dispatch(Msg.UpdatePreviewUiState(UiState.Error())) },
            ) {
                dispatch(Msg.UpdateBestRatedRecipes(recipes = SerializableImmutableList(it)))
            }
        }

        val loadRecentTask = scope.async {
            handleRecipesApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getRecentRecipes()
                },
                onUnhandledError = { dispatch(Msg.UpdatePreviewUiState(it.toUiState())) },
                onErrorStatusCode = { dispatch(Msg.UpdatePreviewUiState(UiState.Error())) },
            ) {
                dispatch(Msg.UpdateRecentRecipes(recipes = SerializableImmutableList(it)))
            }
        }

        scope.launch {
            if (arrayOf(loadBestRatedTask, loadRecentTask).all { it.await() })
                dispatch(Msg.UpdatePreviewUiState(UiState.Success))
        }
    }

    private fun addToMyRecipes(
        recipeUiState: RecipeUiState,
        unhandledErrorSnackbar: SnackbarMessage,
    ) = scope.launch {
        handleModifyRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.addToMyRecipes(recipeId = recipeUiState.id)
            },
            unhandledErrorSnackbar = unhandledErrorSnackbar,
            onApiError = { showSnackbar(unhandledErrorSnackbar) },
            onSuccess = ::loadRecipes,
        )
    }

    private fun removeFromMyRecipes(
        recipeUiState: RecipeUiState,
        unhandledErrorSnackbar: SnackbarMessage,
        recipeCannotBeDeletedSnackbar: SnackbarMessage,
    ) = scope.launch {
        handleModifyRecipeApiResult(
            result = withContext(AppDispatchers.Data) {
                recipeRepository.removeFromMyRecipes(recipeId = recipeUiState.id)
            },
            unhandledErrorSnackbar = unhandledErrorSnackbar,
            onApiError = { (status) ->
                showSnackbar(
                    when (status) {
                        RECIPE_CANNOT_BE_DELETED -> recipeCannotBeDeletedSnackbar
                        else -> unhandledErrorSnackbar
                    }
                )
            },
            onSuccess = ::loadRecipes,
        )
    }

    private fun launchNewSearchTextUpdateQuery(text: String) {
        dispatch(Msg.UpdateFoundRecipesUiState(UiState.Loading))

        currentUpdateSearchTextTask?.cancel()
        currentUpdateSearchTextTask = scope.launch {
            delay(UPDATE_SEARCH_TEXT_DELAY)

            handleRecipesApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getRecipesByName(name = text)
                },
                onUnhandledError = { dispatch(Msg.UpdateFoundRecipesUiState(it.toUiState())) },
                onErrorStatusCode = { dispatch(Msg.UpdateFoundRecipesUiState(UiState.Error())) },
            ) {
                dispatch(
                    Msg.UpdateFoundRecipesUiState(
                        recipesUiState = SerializableImmutableList(it).toUiState()
                    )
                )
            }
        }
    }

    // -------------------- API results handling --------------------

    private suspend inline fun handleRecipesApiResult(
        result: ApiResultWithCode<List<RecipeResponse>>,
        onUnhandledError: (error: Throwable) -> Unit,
        onErrorStatusCode: (status: HttpStatusCode) -> Unit,
        onSuccess: (recipes: ImmutableList<RecipeUiState>) -> Unit,
    ) = handleApiResult(
        result = result,
        onUnhandledError = onUnhandledError,
        onErrorStatusCode = { status ->
            when {
                status.isForbidden -> globalEventRepository.sendLogOut(Reason.MANUAL)
                else -> onErrorStatusCode(status)
            }
        },
    ) { recipes ->
        onSuccess(
            withContext(AppDispatchers.Eval) {
                recipes.mapToImmutableList(RecipeUiState.Companion::fromResponse)
            }
        )
    }

    private suspend inline fun handleModifyRecipeApiResult(
        result: ApiResultWithCode<Unit>,
        unhandledErrorSnackbar: SnackbarMessage,
        onApiError: (HttpStatusCode) -> Unit,
        onSuccess: () -> Unit,
    ) = handleApiResult(
        result = result,
        onUnhandledError = { globalEventRepository.sendSnackbar(unhandledErrorSnackbar) },
        onErrorStatusCode = { status ->
            when {
                status.isForbidden -> globalEventRepository.sendLogOut(Reason.ERROR)
                else -> onApiError(status)
            }
        },
        onSuccess = { onSuccess() },
    )

    private suspend inline fun showSnackbar(snackbarMessage: SnackbarMessage) =
        globalEventRepository.sendSnackbar(snackbarMessage)
}
