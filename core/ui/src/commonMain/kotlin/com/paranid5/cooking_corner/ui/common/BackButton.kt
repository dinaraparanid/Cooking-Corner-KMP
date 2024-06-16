package com.paranid5.cooking_corner.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun BackButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
) = IconButton(
    onClick = onBack,
    modifier = modifier,
) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = null,
        tint = AppTheme.colors.button.primary,
        modifier = Modifier.size(iconSize),
    )
}