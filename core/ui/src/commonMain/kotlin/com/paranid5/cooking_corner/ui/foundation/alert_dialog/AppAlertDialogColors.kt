package com.paranid5.cooking_corner.ui.foundation.alert_dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.paranid5.cooking_corner.ui.theme.AppTheme

class AppAlertDialogColors private constructor(
    val contentColor: Color,
    val containerColor: Color,
    val confirmButtonTextColor: Color,
    val cancelButtonTextColor: Color,
    val disabledButtonTextColor: Color,
) {
    companion object {
        @Composable
        fun create(
            contentColor: Color = AppTheme.colors.text.primary,
            containerColor: Color = AppTheme.colors.background.primary,
            confirmButtonTextColor: Color = AppTheme.colors.text.primary,
            cancelButtonTextColor: Color = AppTheme.colors.text.primary,
            disabledButtonTextColor: Color = AppTheme.colors.text.primary,
        ) = AppAlertDialogColors(
            contentColor = contentColor,
            containerColor = containerColor,
            confirmButtonTextColor = confirmButtonTextColor,
            cancelButtonTextColor = cancelButtonTextColor,
            disabledButtonTextColor = disabledButtonTextColor,
        )
    }
}