package com.paranid5.cooking_corner.data.auth

import com.paranid5.cooking_corner.domain.auth.AuthApi
import io.ktor.client.HttpClient

internal class AuthApiImpl(private val ktorClient: HttpClient) : AuthApi