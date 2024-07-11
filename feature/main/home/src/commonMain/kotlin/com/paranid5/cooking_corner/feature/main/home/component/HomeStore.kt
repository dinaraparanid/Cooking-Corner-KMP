package com.paranid5.cooking_corner.feature.main.home.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.home.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.utils.filterToImmutableList
import com.paranid5.cooking_corner.utils.orNil
import kotlinx.collections.immutable.ImmutableList
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

        data class LikeClick(
            val recipeUiState: RecipeUiState,
            val unhandledErrorMessage: String,
        ) : UiIntent

        data class DislikeClick(
            val recipeUiState: RecipeUiState,
            val unhandledErrorMessage: String,
        ) : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val searchText: String,
        val selectedCategoryIndex: Int,
        val recipesUiState: UiState<ImmutableList<RecipeUiState>>,
        val categoriesUiState: UiState<ImmutableList<CategoryUiState>>,
        val isAscendingOrder: Boolean,
    ) {
        companion object {
            private const val NOT_SELECTED = 0
        }

        @Transient
        val selectedCategoryTitle: String =
            selectedCategoryIndex
                .takeIf { it != NOT_SELECTED }
                ?.let { index ->
                    categoriesUiState
                        .getOrNull()
                        ?.getOrNull(index)
                        ?.title
                }
                .orEmpty()

        @Transient
        private val searchTextLowercase: String =
            searchText.lowercase()

        @Transient
        val filteredRecipes: ImmutableList<RecipeUiState> =
            recipesUiState
                .getOrNull()
                ?.filterToImmutableList { searchTextLowercase in it.title }
                .orNil()

        @Transient
        val categories = categoriesUiState.getOrNull().orNil()

        constructor() : this(
            searchText = "",
            selectedCategoryIndex = NOT_SELECTED,
            recipesUiState = UiState.Undefined,
            categoriesUiState = UiState.Undefined,
            isAscendingOrder = false,
        )
    }

    sealed interface Label {
        data class ShowRecipe(val recipeUiState: RecipeUiState) : Label
        data object ShowAddRecipe : Label
        data object ShowGenerateRecipe : Label
        data object ShowRecipeEditor : Label
    }
}