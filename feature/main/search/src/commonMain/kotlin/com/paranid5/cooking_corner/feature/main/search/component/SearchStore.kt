package com.paranid5.cooking_corner.feature.main.search.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import kotlinx.serialization.Serializable

interface SearchStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data class UpdateSearchText(val text: String) : UiIntent
        data class ShowRecipe(val recipeUiState: RecipeUiState) : UiIntent
        data object AddToRecipesClick : UiIntent
    }

    @Serializable
    @Immutable
    data class State(val searchText: String) {
        constructor() : this(searchText = "")
    }

    sealed interface Label {
        data class ShowRecipe(val recipeUiState: RecipeUiState) : Label
    }
}