package com.paranid5.cooking_corner.data.auth

import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.domain.auth.AuthDataSource
import com.paranid5.cooking_corner.domain.auth.AuthRepository

internal class AuthRepositoryImpl(
    authApi: AuthApi,
    authDataSource: AuthDataSource,
) : AuthRepository,
    AuthApi by authApi,
    AuthDataSource by authDataSource