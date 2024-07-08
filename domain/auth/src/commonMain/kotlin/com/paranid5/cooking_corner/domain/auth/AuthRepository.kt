package com.paranid5.cooking_corner.domain.auth

import arrow.core.Either
import arrow.core.flatten
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.domain.auth.dto.ProfileResponse

interface AuthRepository : AuthApi, AuthDataSource

suspend fun AuthRepository.getMe(): ApiResultWithCode<ProfileResponse> =
    Either.catch { getMe(accessToken = requireAccessToken()) }.flatten()