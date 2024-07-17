package com.paranid5.cooking_corner.feature.main.profile.utils

import com.paranid5.cooking_corner.domain.auth.dto.ProfileDTO
import com.paranid5.cooking_corner.feature.main.profile.entity.ProfileUiState

fun ProfileUiState.Companion.fromResponse(response: ProfileDTO) =
    ProfileUiState(
        username = response.username,
        name = response.name,
        surname = response.surname,
        email = response.email,
        cookingExperienceYears = response.cookingExperienceYears,
        photoUrl = response.imagePath,
    )