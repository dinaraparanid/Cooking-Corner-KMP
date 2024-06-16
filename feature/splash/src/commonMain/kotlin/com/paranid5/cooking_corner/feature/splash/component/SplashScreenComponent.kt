package com.paranid5.cooking_corner.feature.splash.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.UiIntentHandler

interface SplashScreenComponent : UiIntentHandler<SplashScreenUiIntent> {

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onSplashScreenClosed: () -> Unit,
        ): SplashScreenComponent
    }
}