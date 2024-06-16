package com.paranid5.cooking_corner.featrue.auth.component

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthConfig {
    @Serializable
    data object SignIn : AuthConfig

    @Serializable
    data object SignUp : AuthConfig
}