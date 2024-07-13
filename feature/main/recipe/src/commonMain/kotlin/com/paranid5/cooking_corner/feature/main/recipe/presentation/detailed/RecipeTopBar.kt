package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_kebab
import com.paranid5.cooking_corner.core.resources.recipe_private_label
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeUiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedBackButton
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

private val BUTTON_SIZE = 36.dp
private val ICON_SIZE = 20.dp

@Composable
internal fun RecipeTopBar(
    isOwned: Boolean,
    recipeUiState: RecipeDetailedUiState,
    isKebabMenuVisible: Boolean,
    onUiIntent: (RecipeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = ConstraintLayout(modifier) {
    val appPadding = AppTheme.dimensions.padding
    val (backButton, privateStatus, kebabMenu) = createRefs()

    val buttonModifier = Modifier
        .size(BUTTON_SIZE)
        .simpleShadow()
        .background(AppTheme.colors.background.primary)

    AppOutlinedBackButton(
        onClick = { onUiIntent(RecipeUiIntent.Back) },
        iconModifier = Modifier.size(ICON_SIZE),
        modifier = buttonModifier.constrainAs(backButton) {
            centerVerticallyTo(parent)
            start.linkTo(parent.start)
        },
    )

    if (recipeUiState.isPublished.not())
        PrivateStatusLabel(
            modifier = Modifier.constrainAs(privateStatus) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            }
        )

    KebabMenu(
        isOwned = isOwned,
        isKebabMenuVisible = isKebabMenuVisible,
        recipeUiState = recipeUiState,
        onUiIntent = onUiIntent,
        buttonModifier = buttonModifier,
        modifier = Modifier.constrainAs(kebabMenu) {
            centerVerticallyTo(parent)
            end.linkTo(parent.end)
        },
    )
}

@Composable
private fun PrivateStatusLabel(modifier: Modifier = Modifier) = AppMainText(
    text = stringResource(Res.string.recipe_private_label),
    textAlign = TextAlign.Center,
    style = AppTheme.typography.regular,
    overflow = TextOverflow.Ellipsis,
    modifier = modifier,
)

@Composable
private fun KebabMenu(
    isOwned: Boolean,
    isKebabMenuVisible: Boolean,
    recipeUiState: RecipeDetailedUiState,
    onUiIntent: (RecipeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier
) = Column(modifier) {
    if (isOwned) {
        KebabButton(
            onClick = { onUiIntent(RecipeUiIntent.ChangeKebabMenuVisibility(isVisible = true)) },
            modifier = buttonModifier,
        )

        RecipeKebabMenu(
            recipeUiState = recipeUiState,
            isKebabMenuVisible = isKebabMenuVisible,
            onUiIntent = onUiIntent,
        )
    }
}

@Composable
private fun KebabButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = TopButton(
    onClick = onClick,
    modifier = modifier,
    content = { KebabIcon(Modifier.size(ICON_SIZE)) },
)

@Composable
private fun KebabIcon(modifier: Modifier = Modifier) = Image(
    imageVector = vectorResource(Res.drawable.ic_kebab),
    contentDescription = null,
    modifier = modifier,
)

@Composable
private fun TopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) = AppOutlinedRippleButton(
    onClick = onClick,
    content = content,
    modifier = modifier,
    shape = CircleShape,
    contentPadding = PaddingValues(0.dp),
    border = BorderStroke(
        width = AppTheme.dimensions.borders.minimum,
        color = AppTheme.colors.button.primary,
    ),
)
