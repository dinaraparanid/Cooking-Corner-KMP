package com.paranid5.cooking_corner.feature.main.splash.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.UiIntentHandler

interface MainSplashScreenComponent : UiIntentHandler<MainSplashScreenUiIntent> {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onSplashScreenClosed: () -> Unit,
        ): MainSplashScreenComponent
    }
}