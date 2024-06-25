package com.paranid5.cooking_corner.feature.main.home.presentation.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.State as ComposeState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.home_category
import com.paranid5.cooking_corner.core.resources.ic_arrow_down
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.home.domain.CategoryUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.AppSpinner
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun CategorySpinner(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Box(modifier.background(AppTheme.colors.background)) {
    CategorySpinnerImpl(
        state = state,
        onUiIntent = onUiIntent,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterStart)
            .padding(
                vertical = AppTheme.dimensions.padding.small,
                horizontal = AppTheme.dimensions.padding.extraMedium,
            ),
    )

    CategorySpinnerArrow(
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = AppTheme.dimensions.padding.extraBig),
    )
}

@Composable
private fun CategorySpinnerImpl(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val categories by rememberCategoriesTitles(state.categories)

    AppSpinner(
        modifier = modifier,
        items = categories,
        selectedItemIndices = persistentListOf(state.selectedCategoryIndex),
        onItemSelected = { index, _ -> onUiIntent(UiIntent.SelectCategory(index = index)) }
    )
}

@Composable
private fun CategorySpinnerArrow(modifier: Modifier = Modifier) = Image(
    imageVector = vectorResource(Res.drawable.ic_arrow_down),
    contentDescription = null,
    modifier = modifier
)

@Composable
private fun rememberCategoriesTitles(
    categories: ImmutableList<CategoryUiState>
): ComposeState<ImmutableList<String>> {
    val notSelectedTitle = stringResource(Res.string.home_category)
    return remember(categories) {
        derivedStateOf {
            categories
                .map { it.title.ifEmpty { notSelectedTitle } }
                .toImmutableList()
        }
    }
}