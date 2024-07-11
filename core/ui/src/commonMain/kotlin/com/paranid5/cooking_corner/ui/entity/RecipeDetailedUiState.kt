package com.paranid5.cooking_corner.ui.entity

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RecipeDetailedUiState(
    val id: Long,
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
    val coverUrlState: UiState<String>,
    val steps: SerializableImmutableList<StepUiState>,
    val ingredients: SerializableImmutableList<IngredientUiState>,
)