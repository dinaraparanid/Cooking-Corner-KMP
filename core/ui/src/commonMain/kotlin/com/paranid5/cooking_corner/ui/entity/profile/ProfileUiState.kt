package com.paranid5.cooking_corner.ui.entity.profile

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Immutable
data class ProfileUiState(
    val username: String,
    val email: String?,
    val name: String?,
    val surname: String?,
    val cookingExperience: String?,
    val cover: ImageContainer?,
) {
    @Transient
    val cookingExperienceYears = cookingExperience?.toIntOrNull()
}
