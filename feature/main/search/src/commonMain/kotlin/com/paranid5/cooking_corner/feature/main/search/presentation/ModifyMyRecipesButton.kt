package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.home_remove_public_recipe_error
import com.paranid5.cooking_corner.core.resources.search_add_to_recipes
import com.paranid5.cooking_corner.core.resources.search_remove_from_recipes
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeUiState
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ModifyMyRecipesButton(
    recipeUiState: RecipeUiState,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textRes = remember(recipeUiState.isMyRecipe) {
        when {
            recipeUiState.isMyRecipe -> Res.string.search_remove_from_recipes
            else -> Res.string.search_add_to_recipes
        }
    }

    val unhandledErrorSnackbar = UnhandledErrorSnackbar()
    val recipeCannotBeDeletedSnackbar = RecipeCannotBeDeletedSnackbar()

    fun addRecipe() = onUiIntent(
        UiIntent.AddToMyRecipesClick(
            recipeUiState = recipeUiState,
            unhandledErrorSnackbar = unhandledErrorSnackbar,
        )
    )

    fun removeRecipe() = onUiIntent(
        UiIntent.RemoveFromMyRecipesClick(
            recipeUiState = recipeUiState,
            unhandledErrorSnackbar = unhandledErrorSnackbar,
            recipeCannotBeDeletedSnackbar = recipeCannotBeDeletedSnackbar,
        )
    )

    val onClick = remember(recipeUiState) {
        when {
            recipeUiState.isMyRecipe -> ::removeRecipe
            else -> ::addRecipe
        }
    }

    ModifyMyRecipesButtonImpl(
        text = stringResource(textRes),
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun ModifyMyRecipesButtonImpl(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = AppOutlinedRippleButton(
    onClick = onClick,
    shape = RoundedCornerShape(AppTheme.dimensions.corners.small),
    border = BorderStroke(
        width = AppTheme.dimensions.borders.minimum,
        color = AppTheme.colors.button.primary,
    ),
    colors = ButtonDefaults.buttonColors(
        containerColor = AppTheme.colors.button.secondary,
        disabledContainerColor = AppTheme.colors.button.secondary,
    ),
    modifier = modifier.simpleShadow(
        radius = AppTheme.dimensions.corners.small,
    ),
) {
    Text(
        text = text,
        color = AppTheme.colors.text.primary,
        fontWeight = FontWeight.Bold,
        fontFamily = AppTheme.typography.RalewayFontFamily,
        style = AppTheme.typography.captionSm,
    )
}

@Composable
private fun UnhandledErrorSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.something_went_wrong),
    snackbarType = SnackbarType.NEGATIVE,
)

@Composable
private fun RecipeCannotBeDeletedSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.home_remove_public_recipe_error),
    snackbarType = SnackbarType.NEGATIVE,
)
