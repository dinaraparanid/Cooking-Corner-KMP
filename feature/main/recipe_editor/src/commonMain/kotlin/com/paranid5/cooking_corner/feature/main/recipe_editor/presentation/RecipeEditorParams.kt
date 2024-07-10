package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_editor_category
import com.paranid5.cooking_corner.core.resources.recipe_editor_description
import com.paranid5.cooking_corner.core.resources.recipe_editor_name
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.foundation.AppSpinnerWithArrow
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.utils.identity
import com.paranid5.cooking_corner.utils.orNil
import org.jetbrains.compose.resources.stringResource

internal inline val ParamShape
    @Composable
    get() = RoundedCornerShape(AppTheme.dimensions.corners.extraSmall)

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
        items = state.categoriesUiState.getOrNull().orNil(),
        title = ::identity,
        initial = stringResource(Res.string.recipe_editor_category),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimensions.padding.small)
            .clip(ParamShape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = ParamShape,
            ),
    ) { onUiIntent(UiIntent.UpdateSelectedCategory(index = it)) }
}