package com.paranid5.cooking_corner.feature.main.splash.component

sealed interface MainSplashScreenUiIntent {
    data object CloseSplashScreen : MainSplashScreenUiIntent
}