package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_editor_add_ingredient_placeholder
import com.paranid5.cooking_corner.core.resources.recipe_editor_ingredients
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State.IngredientDialogState
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.recipe.IngredientUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.combinedClickableWithRipple
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun IngredientFlowRow(
    ingredients: ImmutableList<IngredientUiState>,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = RecipeEditorFlowRow(
    items = ingredients,
    modifier = modifier,
    title = stringResource(Res.string.recipe_editor_ingredients),
    placeholder = stringResource(Res.string.recipe_editor_add_ingredient_placeholder),
    onAddButtonClick = {
        onUiIntent(UiIntent.Ingredient.UpdateDialogState(isVisible = true))
    },
    onItemClick = {
        onUiIntent(
            UiIntent.Ingredient.UpdateDialogState(
                ingredientDialogState = IngredientDialogState.fromUiState(it),
            )
        )
    },
    onItemRemove = { onUiIntent(UiIntent.Ingredient.Remove(ingredient = it)) }
) { ingredient, onItemClick, onItemRemove, itemModifier ->
    IngredientFlowRowItem(
        ingredientUiState = ingredient,
        modifier = itemModifier,
        onItemClick = onItemClick,
        onItemRemove = onItemRemove,
    )
}

@Composable
private fun IngredientFlowRowItem(
    ingredientUiState: IngredientUiState,
    modifier: Modifier = Modifier,
    onItemClick: (ingredientUiState: IngredientUiState) -> Unit,
    onItemRemove: (ingredientUiState: IngredientUiState) -> Unit,
) = Row(
    modifier.combinedClickableWithRipple(
        bounded = true,
        onClick = { onItemClick(ingredientUiState) },
        onLongClick = { onItemRemove(ingredientUiState) },
    )
) {
    AppMainText(
        text = "${ingredientUiState.title} - ${ingredientUiState.portion}",
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
