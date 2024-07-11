package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.search_best_rated
import com.paranid5.cooking_corner.core.resources.search_last_recipes
import com.paranid5.cooking_corner.feature.main.search.component.SearchComponent
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchUi(
    component: SearchComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    Box(modifier.verticalScroll(rememberScrollState())) {
        when (state.uiState) {
            is UiState.Data, is UiState.Success, is UiState.Refreshing ->
                SearchUiContent(
                    state = state,
                    onUiIntent = onUiIntent,
                    modifier = Modifier.fillMaxSize(),
                )

            is UiState.Error ->
                Text("TODO: Error Stub", Modifier.align(Alignment.Center))

            is UiState.Loading, is UiState.Undefined ->
                AppProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun SearchUiContent(
    state: SearchStore.State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecipeFilter(
        state = state,
        onUiIntent = onUiIntent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.extraMedium),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.big))

    RecipesLabel(
        text = stringResource(Res.string.search_last_recipes),
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecipesRow(
        recipes = state.recentRecipes.value,
        onUiIntent = onUiIntent,
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.big))

    RecipesLabel(
        text = stringResource(Res.string.search_best_rated),
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecipesRow(
        recipes = state.bestRatedRecipes.value,
        onUiIntent = onUiIntent,
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))
}

@Composable
private fun RecipesLabel(
    text: String,
    modifier: Modifier = Modifier,
) = Text(
    text = text,
    modifier = modifier,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h2,
    fontFamily = AppTheme.typography.RalewayFontFamily,
)