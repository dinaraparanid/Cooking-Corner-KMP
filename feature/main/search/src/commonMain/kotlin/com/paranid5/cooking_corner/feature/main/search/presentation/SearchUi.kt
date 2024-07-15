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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.search.component.SearchComponent
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.foundation.AppPullRefreshIndicator
import com.paranid5.cooking_corner.ui.foundation.rememberPullRefreshWithDuration
import com.paranid5.cooking_corner.ui.isLoadingOrRefreshing
import com.paranid5.cooking_corner.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchUi(
    component: SearchComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    val (pullRefreshState, isRefreshingShown) = rememberPullRefreshWithDuration(
        isRefreshing = state.previewUiState.isLoadingOrRefreshing,
        onRefresh = { onUiIntent(UiIntent.LoadRecipes) }
    )

    Box(modifier.pullRefresh(pullRefreshState)) {
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.height(AppTheme.dimensions.padding.small))

            RecipeFilter(
                state = state,
                onUiIntent = onUiIntent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimensions.padding.extraMedium),
            )

            when {
                state.isSearching -> FoundRecipes(
                    state = state,
                    onUiIntent = onUiIntent,
                    modifier = Modifier.fillMaxSize(),
                )

                else -> Preview(
                    state = state,
                    onUiIntent = onUiIntent,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                )
            }
        }

        AppPullRefreshIndicator(
            isRefreshing = isRefreshingShown,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}
