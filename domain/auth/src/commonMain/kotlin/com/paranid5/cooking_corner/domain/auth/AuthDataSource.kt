package com.paranid5.cooking_corner.domain.auth

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    val accessTokenFlow: Flow<String?>
    val refreshTokenFlow: Flow<String?>

    suspend fun storeAccessToken(accessToken: String)
    suspend fun storeRefreshToken(refreshToken: String)
}