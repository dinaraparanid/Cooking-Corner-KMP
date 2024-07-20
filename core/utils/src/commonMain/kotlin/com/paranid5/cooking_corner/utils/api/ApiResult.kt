package com.paranid5.cooking_corner.utils.api

import com.paranid5.cooking_corner.core.common.HttpStatusCode

sealed interface ApiResult<out T> {
    data class UnhandledError(val error: Throwable) : ApiResult<Nothing>

    data object Forbidden : ApiResult<Nothing>

    data class ApiError(val statusCode: HttpStatusCode) : ApiResult<Nothing>

    data class Data<T>(val value: T) : ApiResult<T>
}

fun <T> ApiResult<T>.getOrNull() = when (this) {
    is ApiResult.Data -> value
    else -> null
}
