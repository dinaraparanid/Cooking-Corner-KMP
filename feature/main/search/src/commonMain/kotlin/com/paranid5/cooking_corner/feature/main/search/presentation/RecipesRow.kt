package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.feature.main.recipe.presentation.brief.RecipeItem
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeUiState
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import kotlinx.collections.immutable.ImmutableList

private val RECIPE_WIDTH = 185.dp
private val RECIPE_HEIGHT = 280.dp
private val PADDING_BETWEEN_RECIPES = 8.dp

@Composable
internal fun RecipesRow(
    recipes: ImmutableList<RecipeUiState>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = LazyRow(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(PADDING_BETWEEN_RECIPES),
    contentPadding = PaddingValues(horizontal = PADDING_BETWEEN_RECIPES),
) {
    items(items = recipes) { recipe ->
        RecipeItem(
            recipe = recipe,
            modifier = Modifier
                .size(width = RECIPE_WIDTH, height = RECIPE_HEIGHT)
                .clickableWithRipple(bounded = true) {
                    onUiIntent(UiIntent.ShowRecipe(recipeId = recipe.id))
                },
        ) { modifier ->
            ModifyMyRecipesButton(
                recipeUiState = recipe,
                onUiIntent = onUiIntent,
                modifier = modifier,
            )
        }
    }
}
