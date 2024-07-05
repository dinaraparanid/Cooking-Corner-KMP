package com.paranid5.cooking_corner.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.paranid5.cooking_corner.ui.utils.AppTextSelectionColors

@Composable
fun AppTheme(
    dimensions: AppDimensions = AppDimensions.default,
    typography: AppTypography = AppTypography.default,
    content: @Composable () -> Unit,
) {
    val colors by remember { derivedStateOf { AppColors.create() } }
    val rippleIndication = rememberRipple(color = AppTheme.colors.orange)

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography,
        LocalRippleTheme provides AppRippleTheme,
        LocalIndication provides rippleIndication,
        LocalTextSelectionColors provides AppTextSelectionColors,
    ) {
        MaterialTheme(
            colorScheme = colors.colorScheme,
            typography = MaterialTypography,
            shapes = MaterialShapes,
            content = content,
        )
    }
}

object AppTheme {
    val colors @Composable get() = LocalColors.current
    val dimensions @Composable get() = LocalDimensions.current
    val typography @Composable get() = LocalTypography.current
}