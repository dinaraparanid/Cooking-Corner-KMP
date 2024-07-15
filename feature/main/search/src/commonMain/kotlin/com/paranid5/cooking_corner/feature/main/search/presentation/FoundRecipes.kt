package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppErrorStub
import com.paranid5.cooking_corner.ui.getOrNull
import org.jetbrains.compose.resources.stringResource

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
            AppErrorStub(
                errorMessage = stringResource(Res.string.something_went_wrong),
                modifier = Modifier.align(Alignment.Center),
            )

        is UiState.Loading, is UiState.Undefined ->
            AppProgressIndicator(Modifier.align(Alignment.Center))
    }
}
