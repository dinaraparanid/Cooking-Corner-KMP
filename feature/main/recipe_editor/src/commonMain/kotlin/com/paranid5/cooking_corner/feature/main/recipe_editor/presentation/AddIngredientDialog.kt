package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.add
import com.paranid5.cooking_corner.core.resources.recipe_editor_ingredient_portion_placeholder
import com.paranid5.cooking_corner.core.resources.recipe_editor_ingredient_title_placeholder
import com.paranid5.cooking_corner.core.resources.recipe_editor_new_ingredient
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State.IngredientDialogState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedEditText
import com.paranid5.cooking_corner.ui.foundation.alert_dialog.AppAlertDialog
import com.paranid5.cooking_corner.ui.foundation.alert_dialog.AppAlertDialogCallbacks
import com.paranid5.cooking_corner.ui.foundation.alert_dialog.AppAlertDialogProperties
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AddIngredientDialog(
    ingredientDialogState: IngredientDialogState,
    onConfirmButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onPortionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (ingredientDialogState.isVisible)
        AppAlertDialog(
            confirmButtonTitle = stringResource(Res.string.add),
            modifier = modifier,
            callbacks = AppAlertDialogCallbacks(
                onDismissRequest = onCancelButtonClick,
                onConfirmButtonClick = onConfirmButtonClick,
            ),
            properties = AppAlertDialogProperties.create(shape = DialogShape),
            title = {
                AppMainText(
                    text = stringResource(Res.string.recipe_editor_new_ingredient),
                    fontWeight = FontWeight.Bold,
                    style = AppTheme.typography.h.h3,
                    modifier = Modifier
                        .padding(top = AppTheme.dimensions.padding.medium)
                        .align(Alignment.CenterHorizontally),
                )
            },
        ) {
            Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

            AppOutlinedEditText(
                value = ingredientDialogState.title,
                onValueChange = onTitleChange,
                placeholder = stringResource(Res.string.recipe_editor_ingredient_title_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimensions.padding.medium)
            )

            Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

            AppOutlinedEditText(
                value = ingredientDialogState.portion,
                onValueChange = onPortionChange,
                placeholder = stringResource(
                    Res.string.recipe_editor_ingredient_portion_placeholder
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimensions.padding.medium)
            )
        }
}