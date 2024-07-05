package com.paranid5.cooking_corner.feature.main.recipe.component

sealed interface RecipeChild {
    data object Edit : RecipeChild
}