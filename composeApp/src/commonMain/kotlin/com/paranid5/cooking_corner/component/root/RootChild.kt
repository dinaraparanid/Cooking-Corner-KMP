package com.paranid5.cooking_corner.component.root

import com.paranid5.cooking_corner.featrue.auth.component.AuthComponent
import com.paranid5.cooking_corner.feature.main.root.MainRootComponent
import com.paranid5.cooking_corner.feature.splash.component.SplashScreenComponent

sealed interface RootChild {
    class SplashScreen internal constructor(
        internal val component: SplashScreenComponent
    ) : RootChild

    class Auth internal constructor(
        internal val component: AuthComponent
    ) : RootChild

    class Main internal constructor(
        internal val component: MainRootComponent
    ) : RootChild
}