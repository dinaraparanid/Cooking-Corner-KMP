package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_reviews
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecipeReviews(
    reviews: Int,
    modifier: Modifier = Modifier,
) = Text(
    modifier = modifier,
    text = stringResource(Res.string.recipe_reviews, formatReviews(reviews)),
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.caption,
    fontFamily = AppTheme.typography.RalewayFontFamily,
)

// TODO: Formatting
private fun formatReviews(reviews: Int) = reviews.toString()