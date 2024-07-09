package com.paranid5.cooking_corner.domain.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class SnackbarMessage(
    override val message: String,
    val snackbarType: SnackbarType,
    override val withDismissAction: Boolean = false,
    val isCheckedVisible: Boolean = false,
    val isRetryVisible: Boolean = false,
    val gravity: SnackbarGravity = SnackbarGravity.Bottom(),
    val onAction: (() -> Unit)? = null,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = when (snackbarType) {
        SnackbarType.NEUTRAL, SnackbarType.POSITIVE -> SnackbarDuration.Short
        SnackbarType.NEGATIVE -> SnackbarDuration.Long
    },
) : SnackbarVisuals
