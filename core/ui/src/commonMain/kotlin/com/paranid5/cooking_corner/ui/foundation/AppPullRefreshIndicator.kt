package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.ui.theme.AppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppPullRefreshIndicator(
    isRefreshing: Boolean,
    state: PullRefreshState,
    modifier: Modifier = Modifier,
) = PullRefreshIndicator(
    refreshing = isRefreshing,
    state = state,
    modifier = modifier,
    backgroundColor = AppTheme.colors.orange,
    contentColor = AppTheme.colors.button.primary,
)