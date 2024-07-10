package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun RecipeEditorUi(
    component: RecipeEditorComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    RecipeEditorUiContent(
        state = state,
        onUiIntent = onUiIntent,
        modifier = modifier,
    )
}

@Composable
private fun RecipeEditorUiContent(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier.verticalScroll(rememberScrollState())) {
    RecipeEditorTopBar(
        onUiIntent = onUiIntent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AppTheme.dimensions.padding.small)
            .padding(horizontal = AppTheme.dimensions.padding.large),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.extraMedium))

    RecipeCoverPickerButton(modifier = Modifier.align(Alignment.CenterHorizontally))

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    RecipeEditorParams(
        state = state,
        onUiIntent = onUiIntent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.big)
    )
}
