package com.paranid5.cooking_corner.feature.main.root.component

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRootConfig {
    @Serializable
    data object SplashScreen : MainRootConfig

    @Serializable
    data object Main : MainRootConfig
}