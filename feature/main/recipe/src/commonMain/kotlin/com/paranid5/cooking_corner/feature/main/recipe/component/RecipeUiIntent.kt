package com.paranid5.cooking_corner.feature.main.recipe.component

interface RecipeUiIntent {
    data object Back : RecipeUiIntent
    data object Edit : RecipeUiIntent
}