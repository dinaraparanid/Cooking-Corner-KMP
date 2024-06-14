package com.paranid5.cooking_corner.component.splash

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.UiIntentHandler

internal interface SplashScreenComponent : UiIntentHandler<SplashScreenUiIntent> {

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onSplashScreenClosed: () -> Unit,
        ): SplashScreenComponent
    }
}