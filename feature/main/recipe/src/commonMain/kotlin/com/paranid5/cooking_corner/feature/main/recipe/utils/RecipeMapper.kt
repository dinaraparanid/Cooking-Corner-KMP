package com.paranid5.cooking_corner.feature.main.recipe.utils

import com.paranid5.cooking_corner.domain.recipe.entity.RecipeResponse
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.toUiState

fun RecipeUiState.Companion.fromResponse(response: RecipeResponse) =
    RecipeUiState(
        title = response.name,
        rating = response.rating,
        preparingTime = response.preparingTime,
        cookingTime = response.cookingTime,
        author = response.username,
        isLiked = response.isFavourite,
        coverUrlState = response.iconPath?.toUiState() ?: UiState.Success
    )