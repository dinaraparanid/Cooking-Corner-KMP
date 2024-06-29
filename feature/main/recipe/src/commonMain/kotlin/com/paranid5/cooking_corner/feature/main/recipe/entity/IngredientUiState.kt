package com.paranid5.cooking_corner.feature.main.recipe.entity

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class IngredientUiState(
    val title: String,
    val coverUrlState: UiState<String> = UiState.Undefined,
)
