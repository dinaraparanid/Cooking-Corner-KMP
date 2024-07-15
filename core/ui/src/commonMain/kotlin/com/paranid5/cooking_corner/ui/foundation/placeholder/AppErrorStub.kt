package com.paranid5.cooking_corner.ui.foundation.placeholder

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme

private val DEFAULT_IMAGE_SIZE = 265.dp

@Composable
fun AppErrorStub(
    errorMessage: String,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier.size(DEFAULT_IMAGE_SIZE),
    errorMessageModifier: Modifier = Modifier,
    textStyle: TextStyle = AppTheme.typography.body,
) = AppContentStub(
    modifier = modifier,
    imageModifier = imageModifier,
    descriptionModifier = errorMessageModifier
) {
    AppMainText(
        text = errorMessage,
        style = textStyle,
        fontWeight = FontWeight.Bold,
    )
}