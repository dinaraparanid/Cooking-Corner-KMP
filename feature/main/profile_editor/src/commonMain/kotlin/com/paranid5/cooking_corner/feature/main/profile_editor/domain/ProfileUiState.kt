package com.paranid5.cooking_corner.feature.main.profile_editor.domain

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.domain.auth.dto.ProfileDTO
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.utils.toStringOrEmpty
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ProfileUiState(
    val username: String,
    val email: String,
    val name: String,
    val surname: String,
    val cookingExperience: String,
    val cover: ImageContainer?,
) {
    companion object {
        fun fromResponse(response: ProfileDTO) = ProfileUiState(
            username = response.username,
            email = response.email.orEmpty(),
            name = response.name.orEmpty(),
            surname = response.surname.orEmpty(),
            cookingExperience = response.cookingExperienceYears.toStringOrEmpty(),
            cover = response.imagePath?.let(ImageContainer::Uri),
        )
    }
}
