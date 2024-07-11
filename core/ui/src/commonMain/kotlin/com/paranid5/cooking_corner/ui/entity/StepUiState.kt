package com.paranid5.cooking_corner.ui.entity

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class StepUiState(
    val text: String,
    val coverUrlState: UiState<String> = UiState.Undefined,
)