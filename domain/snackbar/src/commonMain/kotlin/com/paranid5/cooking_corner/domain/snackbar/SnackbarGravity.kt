package com.paranid5.cooking_corner.domain.snackbar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DEFAULT_SNACKBAR_PADDING = 16.dp

@Immutable
sealed interface SnackbarGravity {
    @Immutable
    data class Top(val topPadding: Dp = DEFAULT_SNACKBAR_PADDING) : SnackbarGravity

    @Immutable
    data class Bottom(val bottomPadding: Dp = DEFAULT_SNACKBAR_PADDING) : SnackbarGravity
}