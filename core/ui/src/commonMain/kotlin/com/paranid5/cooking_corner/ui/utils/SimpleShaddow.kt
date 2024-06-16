package com.paranid5.cooking_corner.ui.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.ui.MainBlack
import com.paranid5.cooking_corner.ui.theme.AppTheme

fun Modifier.simpleShadow(
    elevation: Dp = 64.dp,
    color: Color? = null,
    radius: Dp? = null,
) = this.composed {
    shadow(
        elevation = elevation,
        shape = RoundedCornerShape(radius ?: AppTheme.dimensions.corners.medium),
        ambientColor = color ?: MainBlack,
        spotColor = color ?: MainBlack
    )
}