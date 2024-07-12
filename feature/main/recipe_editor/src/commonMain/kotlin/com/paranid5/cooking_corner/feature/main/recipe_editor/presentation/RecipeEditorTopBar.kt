package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_editor_recipe_uploaded
import com.paranid5.cooking_corner.core.resources.recipe_editor_save
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedBackButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.stringResource

private val BUTTON_SIZE = 36.dp

@Composable
internal fun RecipeEditorTopBar(
    isSaveButtonEnabled: Boolean,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val generalModifier = Modifier
        .simpleShadow()
        .background(AppTheme.colors.background.primary)

    val unhandledErrorSnackbar = UnhandledErrorSnackbar()
    val successSnackbar = SuccessSnackbar()

    Row(modifier = modifier) {
        AppOutlinedBackButton(generalModifier.size(BUTTON_SIZE)) { onUiIntent(UiIntent.Back) }

        Spacer(Modifier.weight(1F))

        SaveButton(enabled = isSaveButtonEnabled, modifier = generalModifier) {
            onUiIntent(
                UiIntent.Save(
                    unhandledErrorSnackbar = unhandledErrorSnackbar,
                    successSnackbar = successSnackbar,
                )
            )
        }
    }
}

@Composable
private fun SaveButton(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) = Box(
    modifier
        .border(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
            shape = RoundedCornerShape(AppTheme.dimensions.corners.medium)
        )
        .clickableWithRipple(bounded = true, enabled = enabled, onClick = onClick)
) {
    AppMainText(
        text = stringResource(Res.string.recipe_editor_save),
        style = AppTheme.typography.h.h2,
        modifier = Modifier.padding(
            vertical = AppTheme.dimensions.padding.small,
            horizontal = AppTheme.dimensions.padding.medium,
        )
    )
}

@Composable
private fun UnhandledErrorSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.something_went_wrong),
    snackbarType = SnackbarType.NEGATIVE,
    withDismissAction = true,
)

@Composable
private fun SuccessSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.recipe_editor_recipe_uploaded),
    snackbarType = SnackbarType.POSITIVE,
    withDismissAction = true,
)
