package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_reviews
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecipeReviews(
    reviews: Int,
    modifier: Modifier = Modifier,
) {
    val reviewsArg by rememberFormatReviews(reviews)

    AppMainText(
        modifier = modifier,
        text = stringResource(Res.string.recipe_reviews, reviewsArg),
        style = AppTheme.typography.caption,
    )
}

@Composable
private fun rememberFormatReviews(reviews: Int) = remember(reviews) {
    derivedStateOf {
        when (reviews) {
            in 0..999 -> reviews.toString()
            else -> "${(reviews / 1000)}K+"
        }
    }
}
