package com.paranid5.cooking_corner.feature.main.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow

@Composable
internal fun ProfileButton(
    text: String,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = AppTheme.colors.button.secondary,
        disabledContainerColor = AppTheme.colors.button.secondary,
    ),
) {
    val shape = RoundedCornerShape(AppTheme.dimensions.corners.small)
    val interactionSource = remember { MutableInteractionSource() }

    AppOutlinedRippleButton(
        onClick = onClick,
        shape = shape,
        interactionSource = interactionSource,
        border = BorderStroke(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
        ),
        colors = colors,
        modifier = modifier.simpleShadow(
            radius = AppTheme.dimensions.corners.small,
        ),
    ) {
        val itemsModifier = Modifier
            .padding(vertical = AppTheme.dimensions.padding.small)
            .align(Alignment.CenterVertically)

        ProfileButtonLabel(text = text, modifier = itemsModifier)

        icon?.let { image ->
            Spacer(Modifier.width(AppTheme.dimensions.padding.small))
            ProfileButtonIcon(imageVector = image, modifier = itemsModifier)
        }
    }
}

@Composable
private fun ProfileButtonLabel(
    text: String,
    modifier: Modifier = Modifier,
) = AppMainText(
    modifier = modifier,
    text = text,
    style = AppTheme.typography.h.h2,
)

@Composable
private fun ProfileButtonIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) = Image(
    imageVector = imageVector,
    contentDescription = null,
    modifier = modifier,
)
