package com.paranid5.cooking_corner.feature.main.recipe.component

interface RecipeUiIntent {
    data object Back : RecipeUiIntent
    data class ChangeKebabMenuVisibility(val isVisible: Boolean) : RecipeUiIntent
    data object Publish : RecipeUiIntent
    data object Delete : RecipeUiIntent
    data object Edit : RecipeUiIntent
}