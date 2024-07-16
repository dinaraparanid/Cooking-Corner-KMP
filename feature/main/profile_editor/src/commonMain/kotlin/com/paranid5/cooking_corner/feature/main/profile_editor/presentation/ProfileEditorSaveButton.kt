package com.paranid5.cooking_corner.feature.main.profile_editor.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_edit
import com.paranid5.cooking_corner.core.resources.save
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun ProfileEditorSaveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(AppTheme.dimensions.corners.small)

    AppOutlinedRippleButton(
        onClick = onClick,
        shape = shape,
        border = BorderStroke(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.button.secondary,
            disabledContainerColor = AppTheme.colors.button.secondary,
        ),
        modifier = modifier.simpleShadow(
            radius = AppTheme.dimensions.corners.small,
        ),
    ) {
        val itemsModifier = Modifier
            .padding(vertical = AppTheme.dimensions.padding.small)
            .align(Alignment.CenterVertically)

        ProfileEditorSaveButtonLabel(modifier = itemsModifier)
        Spacer(Modifier.width(AppTheme.dimensions.padding.small))
        ProfileEditorSaveButtonIcon(modifier = itemsModifier)
    }
}

@Composable
private fun ProfileEditorSaveButtonLabel(modifier: Modifier = Modifier) = AppMainText(
    modifier = modifier,
    text = stringResource(Res.string.save),
    style = AppTheme.typography.h.h2,
)

@Composable
private fun ProfileEditorSaveButtonIcon(
    modifier: Modifier = Modifier,
) = Image(
    imageVector = vectorResource(Res.drawable.ic_edit),
    contentDescription = null,
    modifier = modifier,
)
