package com.paranid5.cooking_corner.feature.main.profile.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.feature.main.profile.entity.ProfileUiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ProfileState(val profileUiState: ProfileUiState)