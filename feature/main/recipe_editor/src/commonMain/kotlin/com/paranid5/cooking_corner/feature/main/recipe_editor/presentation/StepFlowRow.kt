package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_editor_add_step_placeholder
import com.paranid5.cooking_corner.core.resources.recipe_editor_steps
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun StepFlowRow(
    steps: ImmutableList<StepUiState>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = RecipeEditorFlowRow(
    items = steps,
    modifier = modifier,
    title = stringResource(Res.string.recipe_editor_steps),
    placeholder = stringResource(Res.string.recipe_editor_add_step_placeholder),
    onAddButtonClick = { onUiIntent(UiIntent.Step.UpdateDialogVisibility(isVisible = true)) },
    onItemRemove = { onUiIntent(UiIntent.Step.Remove(step = it)) },
) { step, onItemRemove, itemModifier ->
    StepFlowRowItem(
        stepUiState = step,
        onItemRemove = onItemRemove,
        modifier = itemModifier,
    )
}

@Composable
private fun StepFlowRowItem(
    stepUiState: StepUiState,
    modifier: Modifier = Modifier,
    onItemRemove: (stepUiState: StepUiState) -> Unit,
) = Row(modifier.clickableWithRipple(bounded = true) { onItemRemove(stepUiState) }) {
    AppMainText(
        text = stepUiState.title,
        style = AppTheme.typography.regular,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(
                vertical = AppTheme.dimensions.padding.extraSmall,
                horizontal = AppTheme.dimensions.padding.small,
            ),
    )
}
