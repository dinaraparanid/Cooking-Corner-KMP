package com.paranid5.cooking_corner.domain.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface AuthDataSource {
    val accessTokenFlow: Flow<String?>
    val refreshTokenFlow: Flow<String?>

    suspend fun storeAccessToken(accessToken: String)
    suspend fun storeRefreshToken(refreshToken: String)
}

suspend fun AuthDataSource.getAccessTokenOrNull() = accessTokenFlow.firstOrNull()

suspend fun AuthDataSource.requireAccessToken() = requireNotNull(getAccessTokenOrNull())

suspend fun AuthDataSource.getRefreshTokenOrNull() = refreshTokenFlow.firstOrNull()

suspend fun AuthDataSource.requireRefreshToken() = requireNotNull(getRefreshTokenOrNull())