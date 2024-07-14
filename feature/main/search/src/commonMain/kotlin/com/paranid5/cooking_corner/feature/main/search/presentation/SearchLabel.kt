package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
internal fun SearchLabel(
    text: String,
    modifier: Modifier = Modifier,
) = AppMainText(
    text = text,
    modifier = modifier,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    style = AppTheme.typography.h.h2,
)
