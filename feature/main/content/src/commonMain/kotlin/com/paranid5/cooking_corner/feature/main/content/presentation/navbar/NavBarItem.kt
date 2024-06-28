package com.paranid5.cooking_corner.feature.main.content.presentation.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple

@Composable
internal fun NavBarItem(
    title: String,
    image: ImageVector,
    isCurrent: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) = NavBarIcon(
    title = title,
    image = image,
    isCurrent = isCurrent,
    modifier = modifier.clickableWithRipple(onClick = onClick)
)

@Composable
private fun NavBarIcon(
    title: String,
    image: ImageVector,
    isCurrent: Boolean,
    modifier: Modifier = Modifier,
) {
    val itemColor by rememberItemColor(isCurrent)

    Box(modifier) {
        Column(Modifier.align(Alignment.Center)) {
            Icon(
                imageVector = image,
                contentDescription = title,
                tint = itemColor,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(Modifier.height(AppTheme.dimensions.padding.small))

            Text(
                text = title,
                color = itemColor,
                style = AppTheme.typography.regular,
                fontFamily = AppTheme.typography.RalewayFontFamily,
            )
        }
    }
}

@Composable
private fun rememberItemColor(isCurrent: Boolean): State<Color> {
    val colors = AppTheme.colors
    return remember(isCurrent, colors) {
        derivedStateOf { colors.getTabColor(isCurrent) }
    }
}