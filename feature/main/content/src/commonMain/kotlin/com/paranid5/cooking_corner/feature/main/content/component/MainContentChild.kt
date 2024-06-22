package com.paranid5.cooking_corner.feature.main.content.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.feature.main.home.component.HomeComponent

@Immutable
sealed interface MainContentChild {
    @Immutable
    data object Search : MainContentChild

    @Immutable
    class Home internal constructor(
        internal val component: HomeComponent
    ) : MainContentChild

    @Immutable
    data object Profile : MainContentChild
}