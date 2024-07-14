package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.getOrNull

@Composable
internal fun FoundRecipes(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier
) = Box(modifier) {
    when (state.foundRecipesUiState) {
        is UiState.Data, is UiState.Refreshing, is UiState.Success ->
            state.foundRecipesUiState.getOrNull()?.let {
                RecipesGrid(
                    foundRecipes = it,
                    onUiIntent = onUiIntent,
                    modifier = Modifier.fillMaxSize(),
                )
            }

        is UiState.Error ->
            Text("TODO: Error Stub", Modifier.align(Alignment.Center))

        is UiState.Loading, is UiState.Undefined ->
            AppProgressIndicator(Modifier.align(Alignment.Center))
    }
}
