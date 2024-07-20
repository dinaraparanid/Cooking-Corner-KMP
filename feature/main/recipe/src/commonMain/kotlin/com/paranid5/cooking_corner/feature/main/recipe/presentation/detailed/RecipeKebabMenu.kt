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
import com.paranid5.cooking_corner.core.resources.home_remove_public_recipe_error
import com.paranid5.cooking_corner.core.resources.recipe_kebab_delete
import com.paranid5.cooking_corner.core.resources.recipe_kebab_edit
import com.paranid5.cooking_corner.core.resources.recipe_kebab_publish
import com.paranid5.cooking_corner.core.resources.recipe_published
import com.paranid5.cooking_corner.core.resources.recipe_removed
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeUiIntent
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import com.paranid5.cooking_corner.utils.persistentListOfNotNull
import kotlinx.collections.immutable.PersistentList
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
        isPublished = recipeUiState.isPublished,
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
    isPublished: Boolean,
    onUiIntent: (RecipeUiIntent) -> Unit,
): PersistentList<RecipeKebabMenuItemData> {
    val publishedSnackbar = PublishedSnackbar()
    val removedSnackbar = RemovedSnackbar()
    val unhandledErrorSnackbar = UnhandledErrorSnackbar()
    val recipeCannotBeDeletedSnackbar = RecipeCannotBeDeletedSnackbar()

    return persistentListOfNotNull(
        buildKebabMenuItem(stringResource(Res.string.recipe_kebab_publish)) {
            onUiIntent(
                RecipeUiIntent.Publish(
                    errorSnackbar = unhandledErrorSnackbar,
                    successSnackbar = publishedSnackbar,
                )
            )
        }.takeIf { isPublished.not() },
        buildKebabMenuItem(stringResource(Res.string.recipe_kebab_edit)) {
            onUiIntent(RecipeUiIntent.Edit)
        },
        buildKebabMenuItem(stringResource(Res.string.recipe_kebab_delete)) {
            onUiIntent(
                RecipeUiIntent.Delete(
                    unhandledErrorSnackbar = unhandledErrorSnackbar,
                    successSnackbar = removedSnackbar,
                    recipeCannotBeDeletedSnackbar = recipeCannotBeDeletedSnackbar,
                )
            )
        }.takeIf { isPublished.not() },
    )
}

@Composable
private fun PublishedSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.recipe_published),
    snackbarType = SnackbarType.POSITIVE,
)

@Composable
private fun RemovedSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.recipe_removed),
    snackbarType = SnackbarType.POSITIVE,
)

@Composable
private fun UnhandledErrorSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.something_went_wrong),
    snackbarType = SnackbarType.POSITIVE,
)

@Composable
private fun RecipeCannotBeDeletedSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.home_remove_public_recipe_error),
    snackbarType = SnackbarType.NEGATIVE,
)

private fun buildKebabMenuItem(title: String, onClick: () -> Unit) =
    RecipeKebabMenuItemData(title = title, onClick = onClick)
