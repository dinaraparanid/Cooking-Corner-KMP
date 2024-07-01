package com.paranid5.cooking_corner.core.common

import arrow.core.Either

typealias ApiResult<L, R> = Either<Throwable, Either<L, R>>
typealias ApiResultWithCode<T> = ApiResult<HttpStatusCode, T>