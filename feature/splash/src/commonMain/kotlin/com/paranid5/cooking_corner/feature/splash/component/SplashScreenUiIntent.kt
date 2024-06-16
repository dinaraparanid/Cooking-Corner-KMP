package com.paranid5.cooking_corner.feature.splash.component

sealed interface SplashScreenUiIntent {
    data object CloseSplashScreen : SplashScreenUiIntent
}