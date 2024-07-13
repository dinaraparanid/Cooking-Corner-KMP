package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed.pager

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
internal fun NoPagerItemsPlaceholder(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    val shape = PagerItemShape

    Column(
        modifier = modifier
            .clip(shape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = shape,
            )
    ) {
        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        AppMainText(
            text = title,
            fontWeight = FontWeight.Bold,
            style = AppTheme.typography.h.h3,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        AppMainText(
            text = description,
            textAlign = TextAlign.Center,
            style = AppTheme.typography.body,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.padding.extraMedium),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))
    }
}
