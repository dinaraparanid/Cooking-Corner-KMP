package com.paranid5.cooking_corner.feature.main.search.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.utils.serializer.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

interface SearchStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data object LoadRecipes : UiIntent

        data object SearchRecipes : UiIntent

        data class UpdateSearchText(val text: String) : UiIntent

        data class ShowRecipe(val recipeUiState: RecipeUiState) : UiIntent

        data class AddToMyRecipesClick(
            val recipeUiState: RecipeUiState,
            val unhandledErrorMessage: String,
        ) : UiIntent

        data class RemoveFromMyRecipesClick(
            val recipeUiState: RecipeUiState,
            val unhandledErrorMessage: String,
        ) : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val searchText: String,
        @Serializable(with = ImmutableListSerializer::class)
        val recentRecipes: ImmutableList<RecipeUiState>, // TODO: ui state
        @Serializable(with = ImmutableListSerializer::class)
        val bestRatedRecipes: ImmutableList<RecipeUiState>, // TODO: ui state
        val uiState: UiState<Unit>,
    ) {
        constructor() : this(
            searchText = "",
            recentRecipes = persistentListOf(),
            bestRatedRecipes = persistentListOf(),
            uiState = UiState.Undefined,
        )
    }

    sealed interface Label {
        data class ShowRecipe(val recipeUiState: RecipeUiState) : Label
    }
}