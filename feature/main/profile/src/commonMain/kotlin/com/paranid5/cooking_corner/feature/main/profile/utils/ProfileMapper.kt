package com.paranid5.cooking_corner.feature.main.profile.utils

import com.paranid5.cooking_corner.domain.auth.dto.ProfileDTO
import com.paranid5.cooking_corner.feature.main.profile.entity.ProfileUiState

private const val IMAGE_BASE_URL = "https://storage.yandexcloud.net/cooking-corner-backet"

fun ProfileUiState.Companion.fromResponse(response: ProfileDTO) =
    ProfileUiState(
        username = response.username,
        name = response.name,
        surname = response.surname,
        email = response.email,
        cookingExperienceYears = response.cookingExperienceYears,
        photoUrl = "$IMAGE_BASE_URL/${response.imagePath}",
    )