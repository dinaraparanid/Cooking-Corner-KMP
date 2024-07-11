package com.paranid5.cooking_corner.data.auth

import arrow.core.raise.either
import arrow.core.raise.ensure
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.requireAccessToken
import com.paranid5.cooking_corner.utils.toAppStatusCode
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

internal suspend inline fun <reified T> AuthRepository.withAuth(
    sendRequest: (accessToken: String) -> HttpResponse,
) = either {
    val accessToken = requireAccessToken()
    val response = sendRequest(accessToken)
    ensure(response.status.isSuccess()) { response.toAppStatusCode() }
    response.body<T>()
}