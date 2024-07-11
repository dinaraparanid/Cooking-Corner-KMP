package com.paranid5.cooking_corner.feature.main.recipe.utils

import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.toUiState

fun RecipeUiState.Companion.fromResponse(response: RecipeResponse) =
    RecipeUiState(
        id = response.id,
        title = response.name,
        rating = response.rating,
        preparingTime = response.preparingTime ?: 0,
        cookingTime = response.cookingTime ?: 0,
        author = response.username,
        isLiked = response.isFavourite,
        isMyRecipe = response.isMyRecipe,
        coverUrlState = response.iconPath?.toUiState() ?: UiState.Success,
    )
