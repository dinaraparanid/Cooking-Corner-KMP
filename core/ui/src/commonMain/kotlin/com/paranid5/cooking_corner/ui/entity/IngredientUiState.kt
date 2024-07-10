package com.paranid5.cooking_corner.ui.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class IngredientUiState(
    val title: String,
    val portion: String,
)
