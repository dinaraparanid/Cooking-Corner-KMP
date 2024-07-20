package com.paranid5.cooking_corner.ui.entity.recipe

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class CategoryUiState(val title: String)