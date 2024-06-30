package com.paranid5.cooking_corner.domain.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizeRequest(
    @SerialName("username") val username: String,
    @SerialName("password") val password: String,
)
