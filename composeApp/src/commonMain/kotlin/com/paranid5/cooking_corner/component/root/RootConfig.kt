package com.paranid5.cooking_corner.component.root

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfig {
    @Serializable
    data object SplashScreen : RootConfig

    @Serializable
    data object Auth : RootConfig

    @Serializable
    data object Main : RootConfig
}