package com.paranid5.cooking_corner.feature.main.profile.entity

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.UiState
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ProfileUiState(
    val username: String,
    val name: String,
    val surname: String,
    val email: String,
    val cookingExperience: Long,
    val photoUrl: UiState<String> = UiState.Undefined,
)