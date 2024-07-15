package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_failed_to_load
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeParamsUiState
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppErrorStub
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

internal val DialogShape = RoundedCornerShape(24.dp)
internal val FLOW_ROW_MIN_HEIGHT = 32.dp

@Composable
fun RecipeEditorUi(
    component: RecipeEditorComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    RecipeEditorUiContent(
        state = state,
        uiState = state.uiState,
        onUiIntent = onUiIntent,
        modifier = modifier,
    )
}

@Composable
private fun RecipeEditorUiContent(
    state: State,
    uiState: UiState<Unit>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
): Unit = Box(modifier) {
    when (uiState) {
        is UiState.Data, is UiState.Success -> RecipeEditorUiContentImpl(
            state = state,
            recipeParamsUiState = state.recipeParamsUiState,
            onUiIntent = onUiIntent,
            modifier = Modifier.fillMaxSize(),
        )

        is UiState.Error -> AppErrorStub(
            errorMessage = stringResource(Res.string.recipe_failed_to_load),
            modifier = Modifier.align(Alignment.Center),
        )

        is UiState.Loading, is UiState.Undefined ->
            AppProgressIndicator(Modifier.align(Alignment.Center))

        is UiState.Refreshing -> RecipeEditorUiContent(
            state = state,
            uiState = uiState.value,
            onUiIntent = onUiIntent,
            modifier = modifier,
        )
    }
}

@Composable
private fun RecipeEditorUiContentImpl(
    state: State,
    recipeParamsUiState: RecipeParamsUiState,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Box(modifier) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        RecipeEditorTopBar(
            state = state,
            onUiIntent = onUiIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimensions.padding.small)
                .padding(horizontal = AppTheme.dimensions.padding.large),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.extraMedium))

        RecipeCoverOrPicker(
            cover = state.recipeParamsUiState.cover,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            onUiIntent(UiIntent.UpdateCover(it))
        }

        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

        RecipeEditorParams(
            state = state,
            recipeParamsUiState = recipeParamsUiState,
            onUiIntent = onUiIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.padding.big)
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))
    }

    if (state.ingredientDialogState.isVisible)
        AddIngredientDialog(
            ingredientDialogState = state.ingredientDialogState,
            onConfirmButtonClick = { onUiIntent(UiIntent.Ingredient.Add) },
            onCancelButtonClick = {
                onUiIntent(UiIntent.Ingredient.UpdateDialogState(isVisible = false))
            },
            onTitleChange = { onUiIntent(UiIntent.Ingredient.UpdateTitle(title = it)) },
            onPortionChange = { onUiIntent(UiIntent.Ingredient.UpdatePortion(portion = it)) },
        )

    if (state.stepDialogState.isVisible)
        AddStepDialog(
            stepDialogState = state.stepDialogState,
            onConfirmButtonClick = { onUiIntent(UiIntent.Step.Add) },
            onCancelButtonClick = {
                onUiIntent(UiIntent.Step.UpdateDialogState(isVisible = false))
            },
            onTitleChange = { onUiIntent(UiIntent.Step.UpdateTitle(title = it)) },
            onDescriptionChange = { onUiIntent(UiIntent.Step.UpdateDescription(description = it)) },
            onPicked = { onUiIntent(UiIntent.Step.UpdateCover(it)) },
        )
}
