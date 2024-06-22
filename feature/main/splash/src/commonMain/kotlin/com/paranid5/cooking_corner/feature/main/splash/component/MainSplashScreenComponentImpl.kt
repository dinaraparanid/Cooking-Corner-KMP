package com.paranid5.cooking_corner.feature.main.splash.component

import com.arkivanov.decompose.ComponentContext

internal class MainSplashScreenComponentImpl(
    componentContext: ComponentContext,
    private val onSplashScreenClosed: () -> Unit,
) : MainSplashScreenComponent, ComponentContext by componentContext {
    override fun onUiIntent(intent: MainSplashScreenUiIntent) =
        when (intent) {
            MainSplashScreenUiIntent.CloseSplashScreen -> onSplashScreenClosed()
        }

    class Factory : MainSplashScreenComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onSplashScreenClosed: () -> Unit,
        ): MainSplashScreenComponent = MainSplashScreenComponentImpl(
            componentContext = componentContext,
            onSplashScreenClosed = onSplashScreenClosed,
        )
    }
}