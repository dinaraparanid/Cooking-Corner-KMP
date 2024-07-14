package com.paranid5.cooking_corner.feature.main.generate.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class GenerateState(
    val link: String,
    val uiState: UiState<Unit>,
) {
    constructor() : this(link = "", uiState = UiState.Undefined)
}