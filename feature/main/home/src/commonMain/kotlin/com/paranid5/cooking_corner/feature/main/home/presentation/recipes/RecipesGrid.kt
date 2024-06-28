package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.utils.pxToDp

private const val MIN_RECIPES_IN_ROW = 2
private val RECIPE_MAX_WIDTH = 185.dp
private val RECIPE_HEIGHT = 280.dp
private val PADDING_BETWEEN_RECIPES = 4.dp

@Composable
internal fun RecipesGrid(
    recipes: LazyPagingItems<RecipeUiState>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier
) = BoxWithConstraints(modifier) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(PADDING_BETWEEN_RECIPES),
        horizontalArrangement = Arrangement.spacedBy(PADDING_BETWEEN_RECIPES),
        columns = GridCells.Adaptive(getMinCellWidth(constraints.maxWidth))
    ) {
        items(count = recipes.itemCount) { index ->
            recipes[index]?.let { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onUiIntent = onUiIntent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(RECIPE_HEIGHT)
                        .clickable { onUiIntent(UiIntent.ShowRecipe(recipe)) },
                )
            }
        }
    }
}

@Composable
private fun getMinCellWidth(screenWidth: Int): Dp {
    val screenWidthDp = screenWidth.pxToDp()
    return remember(screenWidthDp) {
        minOf(
            screenWidthDp / MIN_RECIPES_IN_ROW - PADDING_BETWEEN_RECIPES,
            RECIPE_MAX_WIDTH
        )
    }
}
