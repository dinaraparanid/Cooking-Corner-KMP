package com.paranid5.cooking_corner.feature.main.home.domain

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RecipeUiState(
    val title: String,
    val thumbnail: String,
    val rating: Float,
    val preparingTime: Int,
    val cookingTime: Int,
    val author: String,
)
