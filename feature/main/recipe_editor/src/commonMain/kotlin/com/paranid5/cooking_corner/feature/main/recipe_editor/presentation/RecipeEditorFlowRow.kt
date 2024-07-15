package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_editor_ingredients
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T> RecipeEditorFlowRow(
    items: ImmutableList<T>,
    title: String,
    placeholder: String,
    onAddButtonClick: () -> Unit,
    onItemClick: (item: T) -> Unit,
    onItemRemove: (item: T) -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable (
        item: T,
        onItemClick: (item: T) -> Unit,
        onItemRemove: (item: T) -> Unit,
        modifier: Modifier
    ) -> Unit
) = Column(modifier) {
    AppMainText(
        text = title,
        style = AppTheme.typography.body,
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.minimum))

    FlowRow(
        modifier = ClippedOutlinedModifier
            .padding(vertical = AppTheme.dimensions.padding.small)
            .heightIn(min = FLOW_ROW_MIN_HEIGHT),
    ) {
        val itemShape = RoundedCornerShape(AppTheme.dimensions.corners.medium)

        val itemModifier = Modifier
            .clip(itemShape)
            .padding(AppTheme.dimensions.padding.small)
            .align(Alignment.CenterVertically)
            .simpleShadow()
            .background(
                color = AppTheme.colors.background.primaryDarkest,
                shape = itemShape,
            )
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = itemShape,
            )

        AnimatedVisibility(visible = items.isEmpty()) {
            AppMainText(
                text = placeholder,
                style = AppTheme.typography.body,
                modifier = Modifier
                    .padding(AppTheme.dimensions.padding.extraMedium)
                    .align(Alignment.CenterVertically),
            )
        }

        items.forEach { ingredient ->
            itemContent(ingredient, onItemClick, onItemRemove, itemModifier)
        }

        FlowRowAddButton(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(AppTheme.dimensions.padding.small),
            onClick = onAddButtonClick,
        )
    }
}
