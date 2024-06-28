package com.paranid5.cooking_corner.feature.main.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_edit
import com.paranid5.cooking_corner.core.resources.profile_edit
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun ProfileEditButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(AppTheme.dimensions.corners.small)
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedButton(
        onClick = onClick,
        shape = shape,
        interactionSource = interactionSource,
        border = BorderStroke(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppTheme.colors.button.secondary,
            disabledBackgroundColor = AppTheme.colors.button.secondary,
        ),
        modifier = modifier
            .clip(shape)
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = AppTheme.colors.orange,
                )
            )
            .simpleShadow(
                elevation = AppTheme.dimensions.elevation.extraBig,
                radius = AppTheme.dimensions.corners.small,
            ),
    ) {
        val itemsModifier = Modifier
            .padding(vertical = AppTheme.dimensions.padding.small)
            .align(Alignment.CenterVertically)

        ProfileEditButtonLabel(itemsModifier)
        Spacer(Modifier.width(AppTheme.dimensions.padding.small))
        ProfileEditButtonIcon(itemsModifier)
    }
}

@Composable
private fun ProfileEditButtonLabel(modifier: Modifier = Modifier) = Text(
    modifier = modifier,
    text = stringResource(Res.string.profile_edit),
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h2,
    fontFamily = AppTheme.typography.RalewayFontFamily,
)

@Composable
private fun ProfileEditButtonIcon(modifier: Modifier = Modifier) = Image(
    imageVector = vectorResource(Res.drawable.ic_edit),
    contentDescription = null,
    modifier = modifier,
)
