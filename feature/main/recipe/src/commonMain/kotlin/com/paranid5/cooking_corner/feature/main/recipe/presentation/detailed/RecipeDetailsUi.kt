package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent
import com.paranid5.cooking_corner.feature.main.recipe.presentation.RecipeClippedCover
import com.paranid5.cooking_corner.ui.theme.AppTheme

private val COVER_WIDTH = 325.dp
private val COVER_HEIGHT = 185.dp

@Composable
fun RecipeDetailsUi(
    component: RecipeComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        TopBar(
            isLiked = state.recipe.isLiked,
            onUiIntent = onUiIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.padding.extraBig)
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.extraSmall))

        RecipeClippedCover(
            coverUrlState = state.recipe.coverUrlState,
            modifier = Modifier
                .size(width = COVER_WIDTH, height = COVER_HEIGHT)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.big))

        RecipeTitle(
            title = state.recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.padding.large),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))
    }
}

@Composable
private fun RecipeTitle(
    title: String,
    modifier: Modifier = Modifier,
) = Text(
    text = title,
    modifier = modifier,
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h2,
    fontFamily = AppTheme.typography.RalewayFontFamily,
)