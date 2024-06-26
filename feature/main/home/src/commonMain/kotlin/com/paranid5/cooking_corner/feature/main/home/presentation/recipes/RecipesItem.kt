package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.feature.main.home.domain.RecipeUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme

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