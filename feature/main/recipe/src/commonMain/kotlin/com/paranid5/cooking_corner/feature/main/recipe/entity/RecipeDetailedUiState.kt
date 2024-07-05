package com.paranid5.cooking_corner.feature.main.recipe.entity

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RecipeDetailedUiState(
    val title: String,
    val rating: Float,
    val preparingTime: Int,
    val cookingTime: Int,
    val author: String,
    val isLiked: Boolean,
    val reviews: Int,
    val portions: Int,
    val byUser: Boolean,
    val isPublished: Boolean,
    val coverUrlState: UiState<String> = UiState.Undefined,
)