package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_editor_category
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore
import com.paranid5.cooking_corner.ui.entity.recipe.CategoryUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppSpinnerWithArrow
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecipeEditorSpinner(
    state: RecipeEditorStore.State,
    modifier: Modifier = Modifier,
    onItemSelected: (index: Int) -> Unit,
) = AppSpinnerWithArrow(
    selectedItemIndex = state.selectedCategoryIndex,
    items = state.categories,
    title = CategoryUiState::title,
    initial = stringResource(Res.string.recipe_editor_category),
    modifier = ClippedOutlinedModifier then modifier,
    onItemSelected = onItemSelected,
    selectedItemFactory = { _, text, itemModifier ->
        AppMainText(
            text = text,
            modifier = itemModifier,
            style = AppTheme.typography.regular,
        )
    },
)
