package com.paranid5.cooking_corner.data.auth

internal class AuthApiUrlBuilder(private val baseUrl: String) {
    fun buildRegisterUrl() = "$baseUrl/register"

    fun buildLoginUrl() = "$baseUrl/login"

    fun buildVerifyTokenUrl(token: String) = "$baseUrl/verify-token/$token"

    fun buildGetMeUrl() = "$baseUrl/get_User/me"

    fun buildEditUserDataUrl() = "$baseUrl/edit_user_data"
}