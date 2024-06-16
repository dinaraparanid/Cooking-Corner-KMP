package com.paranid5.cooking_corner.ui.theme

import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal object AppRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = AppTheme.colors.button.primary

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = Color.Blue,
        lightTheme = false,
    )
}