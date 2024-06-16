package com.paranid5.cooking_corner.feature.splash.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import com.paranid5.cooking_corner.component.componentScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SplashScreenComponentImpl(
    componentContext: ComponentContext,
    private val onSplashScreenClosed: () -> Unit,
) : SplashScreenComponent, ComponentContext by componentContext {
    private companion object {
        const val SPLASH_SCREEN_DURATION_MS = 3000L
    }

    init {
        doOnStart { componentScope.launch { onStart() } }
    }

    override fun onUiIntent(intent: SplashScreenUiIntent) = when (intent) {
        is SplashScreenUiIntent.CloseSplashScreen -> onSplashScreenClosed()
    }

    private suspend fun onStart() {
        delay(SPLASH_SCREEN_DURATION_MS)
        onUiIntent(SplashScreenUiIntent.CloseSplashScreen)
    }

    class Factory : SplashScreenComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onSplashScreenClosed: () -> Unit,
        ): SplashScreenComponent = SplashScreenComponentImpl(
            componentContext = componentContext,
            onSplashScreenClosed = onSplashScreenClosed,
        )
    }
}