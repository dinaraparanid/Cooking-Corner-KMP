package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_dot
import com.paranid5.cooking_corner.core.resources.ic_rating_full
import com.paranid5.cooking_corner.core.resources.unit_minute
import com.paranid5.cooking_corner.feature.main.home.domain.RecipeUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.utils.withPrecision
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun RecipeItem(
    recipe: RecipeUiState,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    RecipeCover(
        coverUrlState = recipe.coverUrlState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.small)
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecipeTitle(
        title = recipe.title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.dimensions.padding.small)
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecipeRatingTimeRow(
        rating = recipe.rating,
        totalTime = recipe.totalTime,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = AppTheme.dimensions.padding.minimum,
                horizontal = AppTheme.dimensions.padding.small,
            )
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecipeAuthor(
        author = recipe.author,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.small)
    )
}

@Composable
private fun RecipeTitle(
    title: String,
    modifier: Modifier = Modifier,
) = Text(
    modifier = modifier,
    text = title,
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h3,
    fontFamily = AppTheme.typography.RalewayFontFamily,
)
