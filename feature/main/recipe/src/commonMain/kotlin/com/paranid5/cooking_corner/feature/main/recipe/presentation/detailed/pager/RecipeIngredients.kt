package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed.pager

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_no_ingredients_description
import com.paranid5.cooking_corner.core.resources.recipe_no_ingredients_title
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecipeIngredients(
    ingredients: ImmutableList<IngredientUiState>,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.medium),
) {
    when {
        ingredients.isEmpty() -> NoPagerItemsPlaceholder(
            title = stringResource(Res.string.recipe_no_ingredients_title),
            description = stringResource(Res.string.recipe_no_ingredients_description),
            modifier = Modifier.fillMaxWidth(),
        )

        else -> ingredients.forEach { ingredient ->
            RecipeIngredient(
                ingredientUiState = ingredient,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))
}

@Composable
private fun RecipeIngredient(
    ingredientUiState: IngredientUiState,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(AppTheme.dimensions.corners.extraMedium)
    val itemsModifier = Modifier.padding(vertical = AppTheme.dimensions.padding.small)

    Row(
        modifier = modifier
            .clip(shape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = shape,
            )
    ) {
        RecipeIngredientTitle(
            title = ingredientUiState.title,
            modifier = itemsModifier
                .weight(1F)
                .align(Alignment.CenterVertically),
        )

        RecipeIngredientPortion(
            portions = ingredientUiState.portion,
            modifier = itemsModifier
                .weight(1F)
                .align(Alignment.CenterVertically),
        )
    }
}

@Composable
private fun RecipeIngredientTitle(
    title: String,
    modifier: Modifier = Modifier,
) = Text(
    text = "$title:",
    textAlign = TextAlign.End,
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h3,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)

@Composable
private fun RecipeIngredientPortion(
    portions: String,
    modifier: Modifier = Modifier,
) = Text(
    text = portions,
    textAlign = TextAlign.Center,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h3,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)