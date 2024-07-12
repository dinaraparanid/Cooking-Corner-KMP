package com.paranid5.cooking_corner.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun Modifier.clickableWithRipple(
    bounded: Boolean = false,
    enabled: Boolean = true,
    color: Color = AppTheme.colors.orange,
    onClick: () -> Unit,
) = this.clickable(
    enabled = enabled,
    interactionSource = remember { MutableInteractionSource() },
    indication = rememberRipple(bounded = bounded, color = color),
    onClick = onClick,
)
