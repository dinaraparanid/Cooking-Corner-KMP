package com.paranid5.cooking_corner.utils

import arrow.core.Either
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.HttpStatusCode

inline fun <T> handleApiResult(
    result: ApiResultWithCode<T>,
    onUnhandledError: (error: Throwable) -> Unit,
    onErrorStatusCode: (HttpStatusCode) -> Unit,
    onSuccess: (value: T) -> Unit,
): Boolean = when (result) {
    is Either.Left -> {
        result.value.printStackTrace()
        onUnhandledError(result.value)
        false
    }

    is Either.Right -> when (val status = result.value) {
        is Either.Left -> {
            onErrorStatusCode(status.value)
            false
        }

        is Either.Right -> {
            onSuccess(status.value)
            true
        }
    }
}
