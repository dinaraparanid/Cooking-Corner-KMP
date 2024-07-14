package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.search_matching_results
import com.paranid5.cooking_corner.feature.main.recipe.presentation.brief.RecipeItem
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import com.paranid5.cooking_corner.ui.utils.pxToDp
import com.paranid5.cooking_corner.utils.doNothing
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

private const val MIN_RECIPES_IN_ROW = 2
private val RECIPE_MAX_WIDTH = 185.dp
private val RECIPE_HEIGHT = 280.dp
private val PADDING_BETWEEN_RECIPES = 8.dp

@Composable
internal fun RecipesGrid(
    foundRecipes: ImmutableList<RecipeUiState>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = when {
    foundRecipes.isEmpty() -> FoundRecipesNoItemsPlaceholder(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(top = AppTheme.dimensions.padding.extraLarge)
    )

    else -> RecipesGridContent(
        foundRecipes = foundRecipes,
        onUiIntent = onUiIntent,
        modifier = modifier.padding(horizontal = PADDING_BETWEEN_RECIPES),
    )
}

@Composable
private fun RecipesGridContent(
    foundRecipes: ImmutableList<RecipeUiState>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Spacer(Modifier.height(AppTheme.dimensions.padding.big))

    SearchLabel(
        text = stringResource(Res.string.search_matching_results),
        modifier = Modifier.align(Alignment.CenterHorizontally),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    BoxWithConstraints(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(PADDING_BETWEEN_RECIPES),
            horizontalArrangement = Arrangement.spacedBy(PADDING_BETWEEN_RECIPES),
            columns = GridCells.Adaptive(getMinCellWidth(constraints.maxWidth)),
        ) {
            items(items = foundRecipes) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onErrorButtonClick = doNothing, // TODO: Error handling
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(RECIPE_HEIGHT)
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
