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
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

interface HomeStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data object LoadMyRecipes : UiIntent
        data class UpdateSearchText(val text: String) : UiIntent
        data class SelectCategory(val index: Int) : UiIntent
        data class ShowRecipe(val recipeUiState: RecipeUiState) : UiIntent
        data object AddRecipe : UiIntent
        data object GenerateRecipe : UiIntent
        data object OrderClick : UiIntent
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
        val isAscendingOrder: Boolean,
        val uiState: UiState<Unit>,
    ) {
        @Transient
        val selectedCategoryTitle: String =
            categories.getOrNull(selectedCategoryIndex)?.title.orEmpty()

        @Transient
        private val searchTextLowercase: String =
            searchText.lowercase()

        @Transient
        val filteredRecipes: ImmutableList<RecipeUiState> =
            recipes.filter { searchTextLowercase in it.title }.toImmutableList()

        constructor() : this(
            searchText = "",
            selectedCategoryIndex = 0,
            recipes = persistentListOf(),
            categories = persistentListOf(CategoryUiState.NotSelected),
            isAscendingOrder = false,
            uiState = UiState.Undefined,
        )
    }

    sealed interface Label {
        data class ShowRecipe(val recipeUiState: RecipeUiState) : Label
        data object ShowAddRecipe : Label
        data object ShowGenerateRecipe : Label
        data object ShowRecipeEditor : Label
    }
}