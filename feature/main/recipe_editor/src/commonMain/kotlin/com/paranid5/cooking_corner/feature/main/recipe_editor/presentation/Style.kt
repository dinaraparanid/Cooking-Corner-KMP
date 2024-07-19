package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.ui.theme.AppTheme

internal inline val ParamShape
    @Composable
    get() = RoundedCornerShape(AppTheme.dimensions.corners.extraSmall)

private inline val ClippedModifier
    @Composable
    get() = Modifier.fillMaxWidth().clip(ParamShape)

internal inline val ClippedOutlinedModifier
    @Composable
    get() = ClippedModifier.border(
        width = 1.dp,
        color = AppTheme.colors.button.primary,
        shape = ParamShape,
    )

internal inline val ClippedOutlinedErrorModifier
    @Composable
    get() = ClippedModifier.border(
        width = 1.dp,
        color = AppTheme.colors.error,
        shape = ParamShape,
    )