package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_kebab
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeUiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedBackButton
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.vectorResource

private val BUTTON_SIZE = 36.dp
private val ICON_SIZE = 20.dp

@Composable
internal fun RecipeTopBar(
    recipeUiState: RecipeDetailedUiState,
    isKebabMenuVisible: Boolean,
    onUiIntent: (RecipeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Box(modifier) {
    val buttonModifier = Modifier
        .size(BUTTON_SIZE)
        .simpleShadow()
        .background(AppTheme.colors.background.primary)

    AppOutlinedBackButton(
        onClick = { onUiIntent(RecipeUiIntent.Back) },
        iconModifier = Modifier.size(ICON_SIZE),
        modifier = buttonModifier.align(Alignment.CenterStart),
    )

    Column(Modifier.align(Alignment.CenterEnd)) {
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
