package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import com.paranid5.cooking_corner.feature.main.recipe.presentation.RecipeItem
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState

private val RECIPE_WIDTH = 185.dp
private val RECIPE_HEIGHT = 280.dp
private val PADDING_BETWEEN_RECIPES = 4.dp

@Composable
internal fun RecipesRow(
    recipes: LazyPagingItems<RecipeUiState>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = LazyRow(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(PADDING_BETWEEN_RECIPES)
) {
    items(count = recipes.itemCount) { index ->
        recipes[index]?.let { recipe ->
            RecipeItem(
                recipe = recipe,
                modifier = Modifier
                    .size(width = RECIPE_WIDTH, height = RECIPE_HEIGHT)
                    .clickable { onUiIntent(UiIntent.ShowRecipe(recipe)) },
            ) { modifier ->
                AddToYourRecipesButton(
                    onClick = { onUiIntent(UiIntent.AddToRecipesClick) },
                    modifier = modifier,
                )
            }
        }
    }
}