package com.paranid5.cooking_corner.feature.main.home.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.home.domain.RecipeUiState
import kotlinx.serialization.Serializable

interface HomeStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data class UpdateSearchText(val text: String) : UiIntent
        data class ShowRecipe(val recipeUiState: RecipeUiState) : UiIntent
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