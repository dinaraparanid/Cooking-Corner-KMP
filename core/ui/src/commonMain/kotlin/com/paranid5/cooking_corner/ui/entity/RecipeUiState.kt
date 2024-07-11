package com.paranid5.cooking_corner.ui.entity

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Immutable
data class RecipeUiState(
    val id: Long,
    val title: String,
    val rating: Float,
    val preparingTime: Int,
    val cookingTime: Int,
    val author: String,
    val isLiked: Boolean,
    val isMyRecipe: Boolean,
    val coverUrlState: UiState<String> = UiState.Undefined,
) {
    @Transient
    val totalTime = preparingTime + cookingTime
}
