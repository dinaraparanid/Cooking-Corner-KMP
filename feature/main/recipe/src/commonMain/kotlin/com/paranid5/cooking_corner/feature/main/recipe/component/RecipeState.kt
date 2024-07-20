package com.paranid5.cooking_corner.feature.main.recipe.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.getOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Immutable
data class RecipeState(
    val recipeUiState: UiState<RecipeDetailedUiState>,
    val isKebabMenuVisible: Boolean,
    val username: String?,
) {
    @Transient
    val isOwned = recipeUiState.getOrNull()?.author == username

    @Transient
    val recipeId = recipeUiState.getOrNull()?.id

    constructor() : this(
        recipeUiState = UiState.Undefined,
        isKebabMenuVisible = false,
        username = "",
    )
}