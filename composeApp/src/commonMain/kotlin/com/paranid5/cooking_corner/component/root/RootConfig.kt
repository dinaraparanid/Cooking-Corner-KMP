package com.paranid5.cooking_corner.component.root

import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponent.AuthorizeType
import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfig {
    @Serializable
    data object SplashScreen : RootConfig

    @Serializable
    data object Auth : RootConfig

    @Serializable
    data class Main(val authType: AuthorizeType) : RootConfig
}