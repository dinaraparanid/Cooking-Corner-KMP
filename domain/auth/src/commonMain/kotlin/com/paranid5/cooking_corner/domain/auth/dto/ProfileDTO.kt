package com.paranid5.cooking_corner.domain.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDTO(
    @SerialName("username") val username: String,
    @SerialName("email") val email: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("surname") val surname: String? = null,
    @SerialName("cooking_experience") val cookingExperienceYears: Int? = null,
    @SerialName("image_path") val imagePath: String? = null,
)
