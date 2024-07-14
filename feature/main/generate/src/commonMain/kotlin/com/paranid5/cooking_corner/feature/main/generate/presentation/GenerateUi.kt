package com.paranid5.cooking_corner.feature.main.generate.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateComponent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun GenerateUi(
    component: GenerateComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    Column(modifier) {
        GenerateTopBar(
            onUiIntent = onUiIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimensions.padding.small)
                .padding(horizontal = AppTheme.dimensions.padding.large),
        )

        when (state.uiState) {
            is UiState.Data,
            is UiState.Error,
            is UiState.Refreshing,
            is UiState.Success,
            is UiState.Undefined -> {
                Spacer(Modifier.height(AppTheme.dimensions.padding.medium))
                UrlEditor(
                    state = state,
                    onUiIntent = onUiIntent,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is UiState.Loading -> {
                Spacer(Modifier.height(AppTheme.dimensions.padding.enormous))
                ProgressUi(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
