package com.paranid5.cooking_corner.ui.entity.mappers

import com.paranid5.cooking_corner.domain.auth.dto.ProfileDTO
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.profile.ProfileUiState

private const val IMAGE_BASE_URL = "https://storage.yandexcloud.net/cooking-corner-backet"

fun ProfileUiState.Companion.fromResponse(response: ProfileDTO) =
    ProfileUiState(
        username = response.username,
        name = response.name,
        surname = response.surname,
        email = response.email,
        cookingExperience = response.cookingExperienceYears?.toString(),
        cover = ImageContainer.Uri("$IMAGE_BASE_URL/${response.imagePath}"),
    )