package com.paranid5.cooking_corner.feature.main.generate.component

import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage

sealed interface GenerateUiIntent {
    data object Back : GenerateUiIntent
    data class UpdateLink(val link: String) : GenerateUiIntent
    data class GenerateClick(val generationErrorSnackbar: SnackbarMessage) : GenerateUiIntent
}