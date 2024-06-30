package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_author
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecipeAuthor(
    author: String,
    modifier: Modifier = Modifier,
) = Text(
    text = stringResource(Res.string.recipe_author, author),
    fontWeight = FontWeight.Bold,
    overflow = TextOverflow.Ellipsis,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.regular,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)