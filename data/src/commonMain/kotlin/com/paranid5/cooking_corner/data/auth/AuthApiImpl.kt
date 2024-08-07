package com.paranid5.cooking_corner.data.auth

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.domain.auth.dto.AuthorizeRequest
import com.paranid5.cooking_corner.domain.auth.dto.LoginResponse
import com.paranid5.cooking_corner.domain.auth.dto.ProfileDTO
import com.paranid5.cooking_corner.utils.toAppStatusCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.withContext

private const val USERNAME_BODY_KEY = "username"
private const val PASSWORD_BODY_KEY = "password"
private const val IMAGE_BASE_URL = "https://storage.yandexcloud.net/cooking-corner-backet"

internal class AuthApiImpl(
    private val ktorClient: HttpClient,
    private val urlBuilder: AuthApiUrlBuilder,
) : AuthApi {
    override suspend fun register(
        username: String,
        password: String,
    ): ApiResultWithCode<Unit> = Either.catch {
        suspend fun sendRequest() = withContext(AppDispatchers.Data) {
            ktorClient.post(urlBuilder.buildRegisterUrl()) {
                contentType(ContentType.Application.Json)

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
    ): ApiResultWithCode<LoginResponse> = Either.catch {
        suspend fun sendRequest() = withContext(AppDispatchers.Data) {
            ktorClient.post(urlBuilder.buildLoginUrl()) {
                contentType(ContentType.Application.FormUrlEncoded)

                setBody(
                    FormDataContent(
                        Parameters.build {
                            append(USERNAME_BODY_KEY, username)
                            append(PASSWORD_BODY_KEY, password)
                        }
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
    ): ApiResultWithCode<Unit> = Either.catch {
        suspend fun sendRequest() = withContext(AppDispatchers.Data) {
            ktorClient.get(urlBuilder.buildVerifyTokenUrl(accessToken))
        }

        either {
            val response = sendRequest()
            ensure(response.status.isSuccess()) { response.toAppStatusCode() }
        }
    }

    override suspend fun getMe(accessToken: String): ApiResultWithCode<ProfileDTO> =
        Either.catch {
            suspend fun sendRequest() = withContext(AppDispatchers.Data) {
                ktorClient.post(urlBuilder.buildGetMeUrl()) {
                    bearerAuth(accessToken)
                }
            }

            either {
                val response = sendRequest()
                ensure(response.status.isSuccess()) { response.toAppStatusCode() }
                response.body()
            }
        }

    override suspend fun updateProfile(
        accessToken: String,
        username: String,
        email: String,
        name: String,
        surname: String,
        cookingExperienceYears: Int?,
    ): ApiResultWithCode<Unit> =
        Either.catch {
            suspend fun sendRequest() = withContext(AppDispatchers.Data) {
                ktorClient.post(urlBuilder.buildEditUserDataUrl()) {
                    bearerAuth(accessToken)
                    contentType(ContentType.Application.Json)
                    setBody(
                        ProfileDTO(
                            username = username,
                            email = email,
                            name = name,
                            surname = surname,
                            cookingExperienceYears = cookingExperienceYears,
                        )
                    )
                }
            }

            either {
                val response = sendRequest()
                ensure(response.status.isSuccess()) { response.toAppStatusCode() }
            }
        }

    override suspend fun uploadProfileCover(
        accessToken: String,
        cover: ByteArray,
    ): ApiResultWithCode<Unit> = Either.catch {
        suspend fun sendRequest() = withContext(AppDispatchers.Data) {
            ktorClient.submitFormWithBinaryData(
                url = urlBuilder.buildEditUserImageUrl(),
                formData = formData {
                    append("file", cover, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=\"profile\"")
                    })
                }
            ) {
                bearerAuth(accessToken)
                contentType(ContentType.Image.Any)
            }
        }

        either {
            val response = sendRequest()
            ensure(response.status.isSuccess()) { response.toAppStatusCode() }
        }
    }
}
