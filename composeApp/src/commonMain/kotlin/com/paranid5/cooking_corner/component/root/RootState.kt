package com.paranid5.cooking_corner.component.root

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RootState(val isAuthorizedUiState: UiState<Unit>) {
    constructor() : this(isAuthorizedUiState = UiState.Undefined)
}