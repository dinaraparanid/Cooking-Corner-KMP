package com.paranid5.cooking_corner.feature.main.recipe.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class IngredientUiState(
    val title: String,
    val portion: String,
)
