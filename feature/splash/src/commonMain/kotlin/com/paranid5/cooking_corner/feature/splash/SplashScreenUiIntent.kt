package com.paranid5.cooking_corner.feature.splash

sealed interface SplashScreenUiIntent {
    data object CloseSplashScreen : SplashScreenUiIntent
}