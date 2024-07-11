package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_kebab_delete
import com.paranid5.cooking_corner.core.resources.recipe_kebab_edit
import com.paranid5.cooking_corner.core.resources.recipe_kebab_publish
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeUiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import com.paranid5.cooking_corner.utils.persistentListOfNotNull
import org.jetbrains.compose.resources.stringResource

private data class RecipeKebabMenuItemData(
    val title: String,
    val onClick: () -> Unit,
)

@Composable
internal fun RecipeKebabMenu(
    recipeUiState: RecipeDetailedUiState,
    isKebabMenuVisible: Boolean,
    onUiIntent: (RecipeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val menuItems = buildKebabMenuItems(
        recipeUiState = recipeUiState,
        onUiIntent = onUiIntent,
    )

    DropdownMenu(
        expanded = isKebabMenuVisible,
        modifier = modifier
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
            )
            .background(AppTheme.colors.background.primary),
        onDismissRequest = {
            onUiIntent(RecipeUiIntent.ChangeKebabMenuVisibility(isVisible = false))
        },
    ) {
        menuItems.forEachIndexed { index, (title, onClick) ->
            RecipeKebabMenuItem(
                title = title,
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimensions.padding.medium)
                    .padding(vertical = AppTheme.dimensions.padding.small),
            )

            if (index != menuItems.lastIndex)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = AppTheme.colors.text.primary,
                )
        }
    }
}

@Composable
private fun RecipeKebabMenuItem(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) = Text(
    text = title,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.regular,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier.clickableWithRipple(bounded = true, onClick = onClick)
)

@Composable
private fun buildKebabMenuItems(
    recipeUiState: RecipeDetailedUiState,
    onUiIntent: (RecipeUiIntent) -> Unit,
) = persistentListOfNotNull(
    buildKebabMenuItem(stringResource(Res.string.recipe_kebab_publish)) {
        onUiIntent(RecipeUiIntent.Publish)
    }.takeIf { recipeUiState.byUser && recipeUiState.isPublished.not() },
    buildKebabMenuItem(stringResource(Res.string.recipe_kebab_edit)) {
        onUiIntent(RecipeUiIntent.Edit)
    }.takeIf { recipeUiState.byUser },
    buildKebabMenuItem(stringResource(Res.string.recipe_kebab_delete)) {
        onUiIntent(RecipeUiIntent.Publish)
    }.takeIf { recipeUiState.byUser },
)

private fun buildKebabMenuItem(title: String, onClick: () -> Unit) =
    RecipeKebabMenuItemData(title = title, onClick = onClick)
