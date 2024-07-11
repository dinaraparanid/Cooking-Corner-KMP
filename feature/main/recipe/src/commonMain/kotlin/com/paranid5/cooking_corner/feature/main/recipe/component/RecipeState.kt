package com.paranid5.cooking_corner.feature.main.recipe.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RecipeState(
    val recipeUiState: UiState<RecipeDetailedUiState>,
    val isKebabMenuVisible: Boolean,
) {
    constructor() : this(
        recipeUiState = UiState.Undefined,
        isKebabMenuVisible = false,
    )
}