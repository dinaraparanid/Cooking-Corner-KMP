package com.paranid5.cooking_corner.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.app_name
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun CookingCornerLabel(modifier: Modifier = Modifier) =
    Text(
        text = stringResource(Res.string.app_name),
        style = AppTheme.typography.h.h1.copy(
            shadow = Shadow(
                color = AppTheme.colors.text.primary.copy(alpha = 0.5F),
                offset = Offset(-5F, 10F),
                blurRadius = 10F
            )
        ),
        color = AppTheme.colors.text.primary,
        fontFamily = AppTheme.typography.primaryFontFamily,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )