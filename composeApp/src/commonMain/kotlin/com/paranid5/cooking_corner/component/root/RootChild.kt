package com.paranid5.cooking_corner.component.root

import com.paranid5.cooking_corner.component.splash.SplashScreenComponent

sealed interface RootChild {
    class SplashScreen internal constructor(
        internal val component: SplashScreenComponent
    ) : RootChild

    data object Auth : RootChild

    data object Home : RootChild
}