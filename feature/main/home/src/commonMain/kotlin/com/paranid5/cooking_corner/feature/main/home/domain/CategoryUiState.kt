package com.paranid5.cooking_corner.feature.main.home.domain

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class CategoryUiState(val title: String) {
    companion object {
        val NotSelected = CategoryUiState(title = "")
    }
}