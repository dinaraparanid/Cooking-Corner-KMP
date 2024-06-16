package com.paranid5.cooking_corner.ui.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun AppTheme(
    dimensions: AppDimensions = AppDimensions.default,
    typography: AppTypography = AppTypography.default,
    content: @Composable () -> Unit,
) {
    val colors by remember {
        derivedStateOf { AppColors.create() }
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography,
        LocalRippleTheme provides AppRippleTheme,
    ) {
        MaterialTheme(
            colorScheme = colors.colorScheme,
            typography = MaterialTypography,
            content = content,
        )
    }
}

object AppTheme {
    val colors @Composable get() = LocalColors.current
    val dimensions @Composable get() = LocalDimensions.current
    val typography @Composable get() = LocalTypography.current
}