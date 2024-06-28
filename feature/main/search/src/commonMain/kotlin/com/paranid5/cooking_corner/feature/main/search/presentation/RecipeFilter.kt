package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_search
import com.paranid5.cooking_corner.core.resources.search_filter_placeholder
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.AppTextSelectionColors
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun RecipeFilter(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(AppTheme.dimensions.corners.small)

    TextField(
        value = state.searchText,
        onValueChange = { onUiIntent(UiIntent.UpdateSearchText(it)) },
        singleLine = true,
        shape = shape,
        modifier = modifier.border(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
            shape = shape,
        ),
        placeholder = { RecipeFilterPlaceholder() },
        leadingIcon = { RecipeFilterIcon() },
        colors = TextFieldDefaults.colors(
            focusedTextColor = AppTheme.colors.text.primary,
            unfocusedTextColor = AppTheme.colors.text.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = AppTheme.colors.text.primary,
            focusedContainerColor = AppTheme.colors.background.alternative,
            unfocusedContainerColor = AppTheme.colors.background.alternative,
            selectionColors = AppTextSelectionColors,
        )
    )
}

@Composable
private fun RecipeFilterPlaceholder(modifier: Modifier = Modifier) = Text(
    text = stringResource(Res.string.search_filter_placeholder),
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.regular,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)

@Composable
private fun RecipeFilterIcon(modifier: Modifier = Modifier) = Image(
    imageVector = vectorResource(Res.drawable.ic_search),
    contentDescription = null,
    modifier = modifier,
)