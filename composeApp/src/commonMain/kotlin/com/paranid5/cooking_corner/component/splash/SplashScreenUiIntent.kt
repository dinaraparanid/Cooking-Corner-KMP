package com.paranid5.cooking_corner.component.splash

internal sealed interface SplashScreenUiIntent {
    data object CloseSplashScreen : SplashScreenUiIntent
}