package com.paranid5.cooking_corner.feature.main.content.component

import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent.Factory.LaunchMode
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
    data class RecipeDetails(val recipeId: Long) : MainContentConfig

    @Serializable
    data object GenerateRecipe : MainContentConfig

    @Serializable
    data class RecipeEditor(val launchMode: LaunchMode) : MainContentConfig

    @Serializable
    data object ProfileEditor : MainContentConfig
}