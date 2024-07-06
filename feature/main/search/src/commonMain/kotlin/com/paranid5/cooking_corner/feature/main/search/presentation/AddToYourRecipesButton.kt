package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.search_add_to_recipes
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AddToYourRecipesButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = AppOutlinedRippleButton(
    onClick = onClick,
    shape = RoundedCornerShape(AppTheme.dimensions.corners.small),
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
    Text(
        text = stringResource(Res.string.search_add_to_recipes),
        color = AppTheme.colors.text.primary,
        fontWeight = FontWeight.Bold,
        fontFamily = AppTheme.typography.RalewayFontFamily,
        style = AppTheme.typography.captionSm,
    )
}