package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.home_author
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecipeAuthor(
    author: String,
    modifier: Modifier = Modifier,
) = Row(modifier) {
    RecipeAuthorLabel(Modifier.align(Alignment.CenterVertically))

    Spacer(Modifier.width(AppTheme.dimensions.padding.small))

    DotIcon(Modifier.align(Alignment.CenterVertically))

    Spacer(Modifier.width(AppTheme.dimensions.padding.small))

    RecipeAuthorImpl(
        author = author,
        modifier = Modifier.align(Alignment.CenterVertically),
    )
}

@Composable
private fun RecipeAuthorLabel(modifier: Modifier = Modifier) = Text(
    text = stringResource(Res.string.home_author),
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.caption,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)

@Composable
private fun RecipeAuthorImpl(
    author: String,
    modifier: Modifier = Modifier,
) = Text(
    text = author,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.caption,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)