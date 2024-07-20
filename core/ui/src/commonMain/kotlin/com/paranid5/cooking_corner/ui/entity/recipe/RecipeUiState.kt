package com.paranid5.cooking_corner.ui.entity.recipe

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.entity.ImageContainer
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
    val cover: ImageContainer? = null,
) {
    @Transient
    val totalTime = preparingTime + cookingTime
}
