package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_add
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun FlowRowAddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) = Box(
    modifier
        .simpleShadow()
        .clip(CircleShape)
        .clickableWithRipple(bounded = true, onClick = onClick)
        .background(
            color = AppTheme.colors.background.primaryDarkest,
            shape = CircleShape,
        )
) {
    Image(
        imageVector = vectorResource(Res.drawable.ic_add),
        contentDescription = null,
        modifier = Modifier.align(Alignment.Center)
    )
}