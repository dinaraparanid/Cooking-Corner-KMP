package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_editor_add_ingredient_placeholder
import com.paranid5.cooking_corner.core.resources.recipe_editor_category
import com.paranid5.cooking_corner.core.resources.recipe_editor_cooking_time
import com.paranid5.cooking_corner.core.resources.recipe_editor_description
import com.paranid5.cooking_corner.core.resources.recipe_editor_name
import com.paranid5.cooking_corner.core.resources.recipe_editor_portions
import com.paranid5.cooking_corner.core.resources.recipe_editor_preparation_time
import com.paranid5.cooking_corner.core.resources.recipe_editor_rest_time
import com.paranid5.cooking_corner.core.resources.recipe_editor_tag
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.TagUiState
import com.paranid5.cooking_corner.ui.foundation.AppSpinnerWithArrow
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.utils.mapToImmutableList
import org.jetbrains.compose.resources.stringResource

internal inline val ParamShape
    @Composable
    get() = RoundedCornerShape(AppTheme.dimensions.corners.extraSmall)

internal inline val ClippedOutlinedModifier
    @Composable
    get() = Modifier
        .fillMaxWidth()
        .padding(vertical = AppTheme.dimensions.padding.small)
        .clip(ParamShape)
        .border(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
            shape = ParamShape,
        )

@Composable
internal fun RecipeEditorParams(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.medium)
) {
    RecipeEditorTextField(
        value = state.name,
        placeholder = stringResource(Res.string.recipe_editor_name),
        onValueChange = { onUiIntent(UiIntent.UpdateName(name = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    RecipeEditorTextField(
        value = state.description,
        placeholder = stringResource(Res.string.recipe_editor_description),
        onValueChange = { onUiIntent(UiIntent.UpdateDescription(description = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    AppSpinnerWithArrow(
        selectedItemIndex = state.selectedCategoryIndex,
        items = state.categories,
        title = CategoryUiState::title,
        initial = stringResource(Res.string.recipe_editor_category),
        modifier = ClippedOutlinedModifier,
    ) { onUiIntent(UiIntent.UpdateSelectedCategory(index = it)) }

    AppSpinnerWithArrow(
        selectedItemIndex = state.selectedTagIndex,
        items = state.tags,
        title = TagUiState::title,
        initial = stringResource(Res.string.recipe_editor_tag),
        modifier = ClippedOutlinedModifier,
    ) { onUiIntent(UiIntent.UpdateSelectedTag(index = it)) }

    RecipeEditorTextField(
        value = state.preparationTimeInput,
        placeholder = stringResource(Res.string.recipe_editor_preparation_time),
        onValueChange = { onUiIntent(UiIntent.UpdatePreparationTime(preparationTimeInput = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    RecipeEditorTextField(
        value = state.cookingTimeInput,
        placeholder = stringResource(Res.string.recipe_editor_cooking_time),
        onValueChange = { onUiIntent(UiIntent.UpdateCookingTime(cookingTimeInput = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    RecipeEditorTextField(
        value = state.restTimeInput,
        placeholder = stringResource(Res.string.recipe_editor_rest_time),
        onValueChange = { onUiIntent(UiIntent.UpdateRestTime(restTimeInput = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    RecipeEditorTextField(
        value = state.portionsInput,
        placeholder = stringResource(Res.string.recipe_editor_portions),
        onValueChange = { onUiIntent(UiIntent.UpdatePortions(portionsInput = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    IngredientFlowRow(
        ingredients = state.ingredients,
        placeholder = stringResource(Res.string.recipe_editor_add_ingredient_placeholder),
        modifier = Modifier.fillMaxWidth(),
        onAddButtonClick = {
            onUiIntent(UiIntent.Ingredient.UpdateDialogVisibility(isVisible = true))
        },
    )
}

private fun stub() =
    (1..1).mapToImmutableList { IngredientUiState(title = "Aboba", portion = "$it kg") }
