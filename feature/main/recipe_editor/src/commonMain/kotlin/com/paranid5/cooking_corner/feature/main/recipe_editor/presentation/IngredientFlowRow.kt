package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_editor_ingredients
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

private val MIN_HEIGHT = 64.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun IngredientFlowRow(
    ingredients: ImmutableList<IngredientUiState>,
    placeholder: String,
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier.heightIn(min = MIN_HEIGHT)) {
    AppMainText(
        text = stringResource(Res.string.recipe_editor_ingredients),
        style = AppTheme.typography.body,
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.minimum))

    FlowRow(ClippedOutlinedModifier) {
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

        AnimatedVisibility(visible = ingredients.isEmpty()) {
            AppMainText(
                text = placeholder,
                style = AppTheme.typography.body,
                modifier = Modifier
                    .padding(AppTheme.dimensions.padding.extraMedium)
                    .align(Alignment.CenterVertically),
            )
        }

        ingredients.forEach { ingredient ->
            IngredientFlowRowItem(
                ingredientUiState = ingredient,
                modifier = itemModifier,
            )
        }

        FlowRowAddButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = onAddButtonClick,
        )
    }
}

@Composable
private fun IngredientFlowRowItem(
    ingredientUiState: IngredientUiState,
    modifier: Modifier = Modifier,
) = Row(modifier) {
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
