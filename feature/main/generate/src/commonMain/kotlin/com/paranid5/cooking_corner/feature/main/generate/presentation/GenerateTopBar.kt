package com.paranid5.cooking_corner.feature.main.generate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateUiIntent
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedBackButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow

private val BUTTON_SIZE = 36.dp

@Composable
internal fun GenerateTopBar(
    onUiIntent: (GenerateUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Row(modifier = modifier) {
    AppOutlinedBackButton(
        onClick = { onUiIntent(GenerateUiIntent.Back) },
        modifier = Modifier
            .size(BUTTON_SIZE)
            .simpleShadow()
            .background(AppTheme.colors.background.primary)
    )
}