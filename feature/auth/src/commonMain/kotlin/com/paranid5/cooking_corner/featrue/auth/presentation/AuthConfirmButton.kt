package com.paranid5.cooking_corner.featrue.auth.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow

private val ELEVATION = 24.dp

@Composable
internal fun AuthConfirmButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = Button(
    onClick = onClick,
    modifier = modifier.simpleShadow(
        elevation = ELEVATION,
        radius = AppTheme.dimensions.corners.minimum
    ),
    shape = RoundedCornerShape(AppTheme.dimensions.corners.minimum),
    colors = ButtonDefaults.buttonColors(
        backgroundColor = AppTheme.colors.button.primary,
        disabledBackgroundColor = AppTheme.colors.button.primary,
    ),
) {
    AuthButtonText(
        text = text,
        modifier = Modifier.padding(AppTheme.dimensions.padding.medium),
    )
}

@Composable
private fun AuthButtonText(
    text: String,
    modifier: Modifier = Modifier,
) = Text(
    text = text,
    modifier = modifier,
    color = AppTheme.colors.text.secondary,
    style = AppTheme.typography.h.h2,
    fontWeight = FontWeight.Bold,
    fontFamily = AppTheme.typography.primaryFontFamily,
)