package com.paranid5.cooking_corner.core.common

import arrow.core.Either

typealias ApiResult<L, R> = Result<Either<L, R>>
typealias ApiResultWithCode<T> = ApiResult<HttpStatusCode, T>