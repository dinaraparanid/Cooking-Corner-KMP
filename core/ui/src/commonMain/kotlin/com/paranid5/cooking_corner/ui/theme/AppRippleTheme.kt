package com.paranid5.cooking_corner.ui.theme

import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.paranid5.cooking_corner.ui.OrangeSelect

@Immutable
internal object AppRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = OrangeSelect

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = OrangeSelect,
        lightTheme = false,
    )
}