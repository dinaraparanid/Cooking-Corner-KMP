package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.home_add_to_favourites
import com.paranid5.cooking_corner.core.resources.home_remove_from_favourites
import com.paranid5.cooking_corner.core.resources.ic_like
import com.paranid5.cooking_corner.core.resources.ic_liked
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

private val ICON_SIZE = 21.dp

@Composable
internal fun FavouritesButton(
    isLiked: Boolean,
    onLikedChanged: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonShape = RoundedCornerShape(AppTheme.dimensions.corners.small)

    OutlinedButton(
        onClick = onLikedChanged,
        shape = buttonShape,
        border = BorderStroke(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppTheme.colors.button.secondary,
            disabledBackgroundColor = AppTheme.colors.button.secondary,
        ),
        modifier = modifier.simpleShadow(
            elevation = AppTheme.dimensions.elevation.extraBig,
            radius = AppTheme.dimensions.corners.small,
        )
    ) {
        FavouritesButtonLabel(
            isLiked = isLiked,
            modifier = Modifier.align(Alignment.CenterVertically),
        )

        Spacer(Modifier.width(AppTheme.dimensions.padding.extraSmall))

        FavouritesButtonImage(
            isLiked = isLiked,
            modifier = Modifier
                .size(ICON_SIZE)
                .align(Alignment.CenterVertically),
        )
    }
}

@Composable
private fun FavouritesButtonLabel(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
) = when {
    isLiked -> FavouritesButtonLabelImpl(
        text = stringResource(Res.string.home_add_to_favourites),
        modifier = modifier,
    )

    else -> FavouritesButtonLabelImpl(
        text = stringResource(Res.string.home_remove_from_favourites),
        modifier = modifier,
    )
}

@Composable
private fun FavouritesButtonLabelImpl(
    text: String,
    modifier: Modifier = Modifier,
) = Text(
    text = text,
    modifier = modifier,
    color = AppTheme.colors.text.primary,
    fontWeight = FontWeight.Bold,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    style = AppTheme.typography.caption,
)

@Composable
private fun FavouritesButtonImage(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
) = when {
    isLiked -> FavouritesButtonImageImpl(
        icon = vectorResource(Res.drawable.ic_liked),
        modifier = modifier,
    )

    else -> FavouritesButtonImageImpl(
        icon = vectorResource(Res.drawable.ic_like),
        modifier = modifier,
    )
}

@Composable
private fun FavouritesButtonImageImpl(
    icon: ImageVector,
    modifier: Modifier = Modifier,
) = Image(
    imageVector = icon,
    contentDescription = null,
    modifier = modifier,
)