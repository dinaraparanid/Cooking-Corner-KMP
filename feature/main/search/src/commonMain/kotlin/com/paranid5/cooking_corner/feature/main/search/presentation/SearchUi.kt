package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.search.component.SearchComponent
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun SearchUi(
    component: SearchComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    Column(modifier) {
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
}
