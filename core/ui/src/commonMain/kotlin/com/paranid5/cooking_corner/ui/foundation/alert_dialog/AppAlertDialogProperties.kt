package com.paranid5.cooking_corner.ui.foundation.alert_dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape

class AppAlertDialogProperties private constructor(
    val shape: Shape,
    val colors: AppAlertDialogColors,
    val cancelButtonEnabled: Boolean,
    val confirmButtonEnabled: Boolean,
) {
    companion object {
        @Composable
        fun create(
            shape: Shape = MaterialTheme.shapes.extraSmall,
            colors: AppAlertDialogColors = AppAlertDialogColors.create(),
            cancelButtonEnabled: Boolean = true,
            confirmButtonEnabled: Boolean = true,
        ) = AppAlertDialogProperties(
            shape = shape,
            colors = colors,
            cancelButtonEnabled = cancelButtonEnabled,
            confirmButtonEnabled = confirmButtonEnabled,
        )
    }
}