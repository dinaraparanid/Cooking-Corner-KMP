package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.theme.AppTheme

private val IMAGE_WIDTH = 320.dp
private val IMAGE_HEIGHT = 180.dp

@Composable
internal fun RecipeCoverOrPicker(
    cover: ImageContainer?,
    modifier: Modifier = Modifier,
    onPicked: (ByteArray) -> Unit,
) {
    val imageShape = RoundedCornerShape(AppTheme.dimensions.corners.medium)

    val imageModifier = modifier
        .clip(imageShape)
        .size(width = IMAGE_WIDTH, height = IMAGE_HEIGHT)
        .border(
            width = AppTheme.dimensions.borders.extraSmall,
            color = AppTheme.colors.button.primary,
            shape = imageShape,
        )

    when (cover) {
        null -> RecipeCoverPickerButton(
            modifier = modifier,
            onPicked = onPicked,
        )

        else -> RecipeCover(
            cover = cover,
            modifier = imageModifier,
            onPicked = onPicked,
        )
    }
}
