package com.paranid5.cooking_corner.domain.auth

interface TokenInteractor {
    sealed interface TokenResult {
        data class UnhandledError(val exception: Throwable) : TokenResult
        data object CredentialsMissing : TokenResult
        data object InvalidPassword : TokenResult
        data object Success : TokenResult
    }

    suspend fun tryAcquireTokens(login: String, password: String): TokenResult
    suspend fun tryRefreshTokens(): TokenResult
}