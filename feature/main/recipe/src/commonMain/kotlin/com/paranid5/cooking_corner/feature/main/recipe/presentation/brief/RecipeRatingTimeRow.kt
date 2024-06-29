package com.paranid5.cooking_corner.feature.main.recipe.presentation.brief

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_rating_full
import com.paranid5.cooking_corner.core.resources.unit_minute
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.utils.withPrecision
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun RecipeRatingTimeRow(
    rating: Float,
    totalTime: Int,
    modifier: Modifier = Modifier,
) = Row(modifier) {
    RecipeRating(
        rating = rating,
        modifier = Modifier.align(Alignment.CenterVertically),
    )

    Spacer(Modifier.width(AppTheme.dimensions.padding.small))

    RecipeTotalTime(
        totalTime = totalTime,
        modifier = Modifier.align(Alignment.CenterVertically),
    )
}

@Composable
private fun RecipeRating(
    rating: Float,
    modifier: Modifier = Modifier,
) = Row(modifier) {
    Image(
        imageVector = vectorResource(Res.drawable.ic_rating_full),
        contentDescription = null,
        modifier = Modifier.align(Alignment.CenterVertically),
    )

    Spacer(Modifier.width(AppTheme.dimensions.padding.small))

    Text(
        text = rating.withPrecision(1),
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.caption,
        fontFamily = AppTheme.typography.RalewayFontFamily,
        modifier = Modifier.align(Alignment.CenterVertically),
    )
}

@Composable
private fun RecipeTotalTime(
    totalTime: Int,
    modifier: Modifier = Modifier,
) = Row(modifier) {
    DotIcon(Modifier.align(Alignment.CenterVertically))

    Spacer(Modifier.width(AppTheme.dimensions.padding.small))

    Text(
        text = "$totalTime ${stringResource(Res.string.unit_minute)}",
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.caption,
        fontFamily = AppTheme.typography.RalewayFontFamily,
        modifier = Modifier.align(Alignment.CenterVertically),
    )
}