package com.paranid5.cooking_corner.feature.main.content.component

import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainContentConfig {
    @Serializable
    data object Search : MainContentConfig

    @Serializable
    data object Home : MainContentConfig

    @Serializable
    data object Profile : MainContentConfig

    @Serializable
    data class RecipeDetails(val recipeUiState: RecipeUiState) : MainContentConfig
}