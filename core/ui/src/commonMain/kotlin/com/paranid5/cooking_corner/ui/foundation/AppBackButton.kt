package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.ui.theme.AppTheme

private val DEFAULT_ICON_SIZE = 20.dp

@Composable
fun AppBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier.size(DEFAULT_ICON_SIZE),
) = IconButton(
    onClick = onClick,
    modifier = modifier,
    content = { BackIcon(iconModifier) },
)

@Composable
fun AppOutlinedBackButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier.size(DEFAULT_ICON_SIZE),
    onClick: () -> Unit,
) = AppOutlinedRippleButton(
    onClick = onClick,
    modifier = modifier,
    shape = CircleShape,
    contentPadding = PaddingValues(0.dp),
    colors = ButtonDefaults.buttonColors(
        containerColor = AppTheme.colors.button.secondary,
        disabledContainerColor = AppTheme.colors.button.secondary,
    ),
    border = BorderStroke(
        width = AppTheme.dimensions.borders.minimum,
        color = AppTheme.colors.button.primary,
    ),
    content = { BackIcon(iconModifier) },
)

@Composable
private fun BackIcon(modifier: Modifier = Modifier) = Image(
    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
    contentDescription = null,
    modifier = modifier
)