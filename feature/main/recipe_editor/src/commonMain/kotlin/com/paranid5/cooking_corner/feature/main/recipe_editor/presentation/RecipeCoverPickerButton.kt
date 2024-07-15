package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_select_cover
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.foundation.picker.ImagePickerLauncher
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecipeCoverPickerButton(
    modifier: Modifier = Modifier,
    onPicked: (ByteArray) -> Unit,
) {
    val launchPicker = ImagePickerLauncher(onPicked)

    AppOutlinedRippleButton(
        modifier = modifier,
        shape = RoundedCornerShape(AppTheme.dimensions.corners.small),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.background.primaryDarkest,
            disabledContainerColor = AppTheme.colors.background.primaryDarker,
        ),
        border = BorderStroke(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
        ),
        contentPadding = PaddingValues(
            vertical = AppTheme.dimensions.padding.medium,
            horizontal = AppTheme.dimensions.padding.extraBig,
        ),
        onClick = { launchPicker() }
    ) {
        AppMainText(
            text = stringResource(Res.string.recipe_select_cover),
            style = AppTheme.typography.h.h3,
            fontWeight = FontWeight.Bold,
        )
    }
}
