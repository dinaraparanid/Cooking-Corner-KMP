package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.recipe.entity.IngredientUiState
import com.paranid5.cooking_corner.feature.main.recipe.entity.StepUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

private const val PAGES_NUMBER = 2
internal const val PAGE_STEPS = 0
internal const val PAGE_INGREDIENTS = 1

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RecipePager(
    steps: ImmutableList<StepUiState>,
    ingredients: ImmutableList<IngredientUiState>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { PAGES_NUMBER })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier) {
        RecipePagerTabs(
            activePage = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.padding.large),
        ) {
            coroutineScope.launch { pagerState.animateScrollToPage(page = it) }
        }

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) { page ->
            val pageModifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.dimensions.padding.extraLarge)

            when (page) {
                PAGE_STEPS -> RecipeCookingSteps(
                    steps = steps,
                    modifier = pageModifier,
                )

                PAGE_INGREDIENTS -> RecipeIngredients(
                    ingredients = ingredients,
                    modifier = pageModifier,
                )
            }
        }
    }
}