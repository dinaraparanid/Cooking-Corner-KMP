package com.paranid5.cooking_corner.domain.auth

import arrow.core.Either
import arrow.core.flatten
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.domain.auth.dto.ProfileDTO

interface AuthRepository : AuthApi, AuthDataSource

suspend inline fun AuthRepository.getMe(): ApiResultWithCode<ProfileDTO> =
    Either.catch { getMe(accessToken = requireAccessToken()) }.flatten()

suspend inline fun AuthRepository.updateProfile(
    username: String,
    email: String,
    name: String,
    surname: String,
    cookingExperienceYears: Int?,
): ApiResultWithCode<Unit> = Either.catch {
    updateProfile(
        accessToken = requireAccessToken(),
        username = username,
        email = email,
        name = name,
        surname = surname,
        cookingExperienceYears = cookingExperienceYears,
    )
}.flatten()

suspend inline fun AuthRepository.uploadProfileCover(cover: ByteArray): ApiResultWithCode<Unit> =
    Either.catch { uploadProfileCover(accessToken = requireAccessToken(), cover = cover) }.flatten()
