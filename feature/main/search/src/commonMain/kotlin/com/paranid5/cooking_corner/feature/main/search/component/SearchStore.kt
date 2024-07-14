package com.paranid5.cooking_corner.feature.main.search.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import kotlinx.serialization.Serializable

interface SearchStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data object LoadRecipes : UiIntent

        data object SearchRecipes : UiIntent

        data class UpdateSearchText(val text: String) : UiIntent

        data object CancelSearching : UiIntent

        data class ShowRecipe(val recipeId: Long) : UiIntent

        data class AddToMyRecipesClick(
            val recipeUiState: RecipeUiState,
            val unhandledErrorSnackbar: SnackbarMessage,
        ) : UiIntent

        data class RemoveFromMyRecipesClick(
            val recipeUiState: RecipeUiState,
            val unhandledErrorSnackbar: SnackbarMessage,
        ) : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val searchText: String,
        val isSearching: Boolean,
        val recentRecipes: SerializableImmutableList<RecipeUiState>,
        val bestRatedRecipes: SerializableImmutableList<RecipeUiState>,
        val foundRecipesUiState: UiState<SerializableImmutableList<RecipeUiState>>,
        val previewUiState: UiState<Unit>,
    ) {
        constructor() : this(
            searchText = "",
            isSearching = false,
            recentRecipes = SerializableImmutableList(),
            bestRatedRecipes = SerializableImmutableList(),
            foundRecipesUiState = UiState.Undefined,
            previewUiState = UiState.Undefined,
        )
    }

    sealed interface Label {
        data class ShowRecipe(val recipeId: Long) : Label
    }
}