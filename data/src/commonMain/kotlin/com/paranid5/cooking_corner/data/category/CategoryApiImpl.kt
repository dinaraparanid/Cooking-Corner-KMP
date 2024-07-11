package com.paranid5.cooking_corner.data.category

import arrow.core.Either
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.data.auth.withAuth
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.category.CategoryApi
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import kotlinx.coroutines.withContext

internal class CategoryApiImpl(
    private val ktorClient: HttpClient,
    private val urlBuilder: CategoryApiUrlBuilder,
    private val authRepository: AuthRepository,
) : CategoryApi {
    override suspend fun getAll(): ApiResultWithCode<List<String>> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.get(urlBuilder.buildGetAllUrl()) {
                    bearerAuth(accessToken)
                }
            }
        }
    }
}