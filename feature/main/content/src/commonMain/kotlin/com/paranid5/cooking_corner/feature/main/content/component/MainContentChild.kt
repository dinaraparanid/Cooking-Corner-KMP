package com.paranid5.cooking_corner.feature.main.content.component

import androidx.compose.runtime.Immutable

@Immutable
sealed interface MainContentChild {
    @Immutable
    data object Search : MainContentChild

    @Immutable
    data object Home : MainContentChild

    @Immutable
    data object Profile : MainContentChild
}