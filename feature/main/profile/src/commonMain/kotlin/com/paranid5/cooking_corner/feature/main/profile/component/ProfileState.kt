package com.paranid5.cooking_corner.feature.main.profile.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.profile.ProfileUiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ProfileState(val uiState: UiState<ProfileUiState>) {
    constructor() : this(uiState = UiState.Undefined)
}