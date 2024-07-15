package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppIconButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) = IconButton(
    onClick = onClick,
    modifier = modifier,
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
    )
}

@Composable
fun AppIconButton(
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit,
) = IconButton(
    onClick = onClick,
    modifier = modifier,
) {
    Icon(
        imageVector = icon,
        tint = tint,
        contentDescription = null,
        modifier = iconModifier,
    )
}