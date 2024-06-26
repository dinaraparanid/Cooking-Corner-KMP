package com.paranid5.cooking_corner.feature.main.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.cash.paging.compose.collectAsLazyPagingItems
import com.paranid5.cooking_corner.feature.main.home.component.HomeComponent
import com.paranid5.cooking_corner.feature.main.home.presentation.recipes.RecipesGrid
import com.paranid5.cooking_corner.feature.main.home.presentation.topbar.HomeTopBar
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun HomeUi(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    val state by component.stateFlow.collectAsState()
    val recipes = component.recepiesPagedFlow.collectAsLazyPagingItems()
    val onUiIntent = component::onUiIntent

    Column(modifier) {
        RecipeFilter(
            state = state,
            onUiIntent = onUiIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.padding.extraBig)
                .padding(top = AppTheme.dimensions.padding.extraSmall),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

        HomeTopBar(
            state = state,
            onUiIntent = onUiIntent,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        RecipesGrid(
            recipes = recipes,
            onUiIntent = onUiIntent,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.dimensions.padding.extraSmall)
        )
    }
}