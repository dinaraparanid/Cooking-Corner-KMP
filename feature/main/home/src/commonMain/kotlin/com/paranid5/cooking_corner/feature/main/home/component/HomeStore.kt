package com.paranid5.cooking_corner.feature.main.home.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.home.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

interface HomeStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data class UpdateSearchText(val text: String) : UiIntent
        data class SelectCategory(val index: Int) : UiIntent
        data class ShowRecipe(val recipeUiState: RecipeUiState) : UiIntent
        data object AddRecipe : UiIntent
        data object GenerateRecipe : UiIntent
        data object DescendingFilterClick : UiIntent
        data object ShowFavourites : UiIntent
        data object LikeClick : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val searchText: String,
        val selectedCategoryIndex: Int,
        val recipes: ImmutableList<RecipeUiState>,
        val categories: ImmutableList<CategoryUiState>,
        val uiState: UiState<Unit>,
    ) {
        constructor() : this(
            searchText = "",
            selectedCategoryIndex = 0,
            recipes = persistentListOf(),
            categories = persistentListOf(CategoryUiState.NotSelected),
            uiState = UiState.Undefined,
        )
    }

    sealed interface Label {
        data class ShowRecipe(val recipeUiState: RecipeUiState) : Label
        data object ShowAddRecipe : Label
        data object ShowGenerateRecipe : Label
    }
}