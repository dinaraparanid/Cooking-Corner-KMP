package com.paranid5.cooking_corner.domain.auth

import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.domain.auth.dto.LoginResponse
import com.paranid5.cooking_corner.domain.auth.dto.ProfileDTO

interface AuthApi {
    suspend fun register(
        username: String,
        password: String,
    ): ApiResultWithCode<Unit>

    suspend fun login(
        username: String,
        password: String,
    ): ApiResultWithCode<LoginResponse>

    suspend fun verifyToken(accessToken: String): ApiResultWithCode<Unit>

    suspend fun getMe(accessToken: String): ApiResultWithCode<ProfileDTO>

    suspend fun updateProfile(
        accessToken: String,
        username: String,
        email: String,
        name: String,
        surname: String,
        cookingExperienceYears: Int?,
    ): ApiResultWithCode<Unit>
}
