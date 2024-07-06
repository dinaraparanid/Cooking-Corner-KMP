package com.paranid5.cooking_corner.data.auth

import arrow.core.Either
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.TokenInteractor
import com.paranid5.cooking_corner.domain.auth.TokenInteractor.TokenResult
import com.paranid5.cooking_corner.domain.auth.getLoginOrNull
import com.paranid5.cooking_corner.domain.auth.getPasswordOrNull
import kotlinx.coroutines.withContext

internal class TokenInteractorImpl(
    private val authRepository: AuthRepository,
) : TokenInteractor {
    override suspend fun tryAcquireTokens(login: String, password: String): TokenResult =
        when (
            val loginRes = withContext(AppDispatchers.Data) {
                authRepository.login(
                    username = login,
                    password = password,
                )
            }
        ) {
            is Either.Left -> {
                loginRes.value.printStackTrace()
                TokenResult.UnhandledError(loginRes.value)
            }

            is Either.Right -> when (val res = loginRes.value) {
                is Either.Left -> TokenResult.InvalidPassword

                is Either.Right -> {
                    authRepository.storeAccessToken(accessToken = res.value.accessToken)
                    authRepository.storeRefreshToken(refreshToken = res.value.refreshToken)
                    TokenResult.Success
                }
            }
        }

    override suspend fun tryRefreshTokens(): TokenResult {
        val login = authRepository.getLoginOrNull() ?: return TokenResult.CredentialsMissing
        val password = authRepository.getPasswordOrNull() ?: return TokenResult.CredentialsMissing
        return tryAcquireTokens(login = login, password = password)
    }
}