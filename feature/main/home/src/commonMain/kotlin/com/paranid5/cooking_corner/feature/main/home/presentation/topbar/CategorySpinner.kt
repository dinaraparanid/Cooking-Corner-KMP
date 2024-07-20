package com.paranid5.cooking_corner.feature.main.home.presentation.topbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.recipe.CategoryUiState
import com.paranid5.cooking_corner.ui.foundation.AppSpinnerWithArrow

@Composable
internal fun CategorySpinner(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = AppSpinnerWithArrow(
    selectedItemIndex = state.selectedCategoryIndex,
    items = state.categories,
    title = CategoryUiState::title,
    modifier = modifier,
) { onUiIntent(UiIntent.SelectCategory(index = it)) }
