package com.paranid5.cooking_corner.feature.main.root.component

import com.paranid5.cooking_corner.feature.main.splash.component.MainSplashScreenComponent

sealed interface MainRootChild {
    class SplashScreen internal constructor(
        internal val component: MainSplashScreenComponent
    ) : MainRootChild

    data object Main : MainRootChild
}