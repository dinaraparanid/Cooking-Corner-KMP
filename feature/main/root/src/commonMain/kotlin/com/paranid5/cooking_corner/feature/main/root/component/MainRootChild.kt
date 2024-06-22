package com.paranid5.cooking_corner.feature.main.root.component

import com.paranid5.cooking_corner.feature.main.content.component.MainContentComponent
import com.paranid5.cooking_corner.feature.main.splash.component.MainSplashScreenComponent

sealed interface MainRootChild {
    class SplashScreen internal constructor(
        internal val component: MainSplashScreenComponent
    ) : MainRootChild

    class Content internal constructor(
        internal val component: MainContentComponent
    ) : MainRootChild
}