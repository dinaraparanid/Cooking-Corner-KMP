package com.paranid5.cooking_corner.data.auth

import arrow.core.raise.either
import arrow.core.raise.ensure
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.domain.auth.dto.AuthorizeRequest
import com.paranid5.cooking_corner.domain.auth.dto.LoginResponse
import com.paranid5.cooking_corner.utils.toAppStatusCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import kotlinx.coroutines.withContext

internal class AuthApiImpl(
    private val ktorClient: HttpClient,
    private val urlBuilder: AuthApiUrlBuilder,
) : AuthApi {
    override suspend fun register(
        username: String,
        password: String,
    ): ApiResultWithCode<Unit> = runCatching {
        suspend fun sendRequest() = withContext(AppDispatchers.Data) {
            ktorClient.post(urlBuilder.buildRegisterUrl()) {
                setBody(
                    AuthorizeRequest(
                        username = username,
                        password = password,
                    )
                )
            }
        }

        either {
            val response = sendRequest()
            ensure(response.status.isSuccess()) { response.toAppStatusCode() }
        }
    }

    override suspend fun login(
        username: String,
        password: String,
    ): ApiResultWithCode<LoginResponse> = runCatching {
        suspend fun sendRequest() = withContext(AppDispatchers.Data) {
            ktorClient.post(urlBuilder.buildLoginUrl()) {
                setBody(
                    AuthorizeRequest(
                        username = username,
                        password = password,
                    )
                )
            }
        }

        either {
            val response = sendRequest()
            ensure(response.status.isSuccess()) { response.toAppStatusCode() }
            response.body()
        }
    }

    override suspend fun verifyToken(
        accessToken: String,
    ): ApiResultWithCode<Unit> = runCatching {
        suspend fun sendRequest() = withContext(AppDispatchers.Data) {
            ktorClient.get(urlBuilder.buildVerifyTokenUrl(accessToken))
        }

        either {
            val response = sendRequest()
            ensure(response.status.isSuccess()) { response.toAppStatusCode() }
        }
    }
}