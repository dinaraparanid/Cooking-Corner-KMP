package com.paranid5.cooking_corner.feature.main.home.domain

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RecipeUiState(
    val title: String,
    val rating: Float,
    val preparingTime: Int,
    val cookingTime: Int,
    val author: String,
    val coverUrlState: UiState<String> = UiState.Undefined,
)
