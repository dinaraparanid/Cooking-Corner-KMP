package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple

@Composable
internal fun TopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) = Box(
    content = content,
    modifier = Modifier
        .clip(CircleShape)
        .then(modifier)
        .border(
            width = AppTheme.dimensions.borders.minimum,
            shape = CircleShape,
            color = AppTheme.colors.button.primary,
        )
        .clickableWithRipple { onClick() },
)