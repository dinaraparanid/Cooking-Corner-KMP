package com.paranid5.cooking_corner.ui.foundation

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

private val DEFAULT_ICON_SIZE = 24.dp

@Composable
fun AppBackButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = DEFAULT_ICON_SIZE,
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