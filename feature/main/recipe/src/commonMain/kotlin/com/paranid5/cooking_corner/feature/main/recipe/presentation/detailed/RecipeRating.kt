package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

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
import com.paranid5.cooking_corner.core.resources.ic_star
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.utils.withPrecision
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun RecipeRating(
    rating: Float,
    modifier: Modifier = Modifier,
) = Row(modifier = modifier) {
    Image(
        imageVector = vectorResource(Res.drawable.ic_star),
        contentDescription = null,
        modifier = Modifier.align(Alignment.CenterVertically),
    )

    Spacer(Modifier.width(AppTheme.dimensions.padding.extraSmall))

    Text(
        text = rating.withPrecision(1),
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.captionSm,
        fontFamily = AppTheme.typography.RalewayFontFamily,
        modifier = Modifier.align(Alignment.CenterVertically),
    )
}