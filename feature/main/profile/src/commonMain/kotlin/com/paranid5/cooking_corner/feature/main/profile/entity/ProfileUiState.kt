package com.paranid5.cooking_corner.feature.main.profile.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ProfileUiState(
    val username: String,
    val name: String?,
    val surname: String?,
    val email: String?,
    val cookingExperienceYears: Int?,
    val photoUrl: String?,
)