package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.search_best_rated
import com.paranid5.cooking_corner.core.resources.search_last_recipes
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppErrorStub
import com.paranid5.cooking_corner.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Preview(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Box(modifier) {
    when (state.previewUiState) {
        is UiState.Data, is UiState.Success, is UiState.Refreshing ->
            PreviewContent(
                state = state,
                onUiIntent = onUiIntent,
                modifier = Modifier.fillMaxSize(),
            )

        is UiState.Error ->
            AppErrorStub(
                errorMessage = stringResource(Res.string.something_went_wrong),
                modifier = Modifier.align(Alignment.Center),
            )

        is UiState.Loading, is UiState.Undefined ->
            AppProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
private fun PreviewContent(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Spacer(Modifier.height(AppTheme.dimensions.padding.big))

    SearchLabel(
        text = stringResource(Res.string.search_last_recipes),
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecentRecipesRow(
        recipes = state.recentRecipes,
        onUiIntent = onUiIntent,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.big))

    SearchLabel(
        text = stringResource(Res.string.search_best_rated),
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecipesRow(
        recipes = state.bestRatedRecipes,
        onUiIntent = onUiIntent,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))
}

@Composable
private fun RecentRecipesRow(
    recipes: ImmutableList<RecipeUiState>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = when {
    recipes.isEmpty() -> LatestRecipesNoItemsPlaceholder(modifier)

    else -> RecipesRow(
        recipes = recipes,
        onUiIntent = onUiIntent,
        modifier = modifier,
    )
}
