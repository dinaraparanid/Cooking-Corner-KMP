package com.paranid5.cooking_corner.data.recipe

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.requireAccessToken
import com.paranid5.cooking_corner.domain.recipe.RecipeApi
import com.paranid5.cooking_corner.domain.recipe.entity.RecipeResponse
import com.paranid5.cooking_corner.utils.toAppStatusCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.withContext

internal class RecipeApiImpl(
    private val ktorClient: HttpClient,
    private val urlBuilder: RecipeApiUrlBuilder,
    private val authRepository: AuthRepository,
) : RecipeApi {
    override suspend fun getRecentRecipes(): ApiResultWithCode<List<RecipeResponse>> =
        Either.catch {
            handeRequest { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.get(urlBuilder.buildGetRecentRecipesUrl()) {
                        bearerAuth(accessToken)
                    }
                }
            }
        }

    override suspend fun getBestRatedRecipes(): ApiResultWithCode<List<RecipeResponse>> =
        Either.catch {
            handeRequest { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.get(urlBuilder.buildGetBestRatedRecipesUrl()) {
                        bearerAuth(accessToken)
                    }
                }
            }
        }

    override suspend fun getMyRecipes(): ApiResultWithCode<List<RecipeResponse>> =
        Either.catch {
            handeRequest { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.post(urlBuilder.buildMyRecipesUrl()) {
                        bearerAuth(accessToken)
                    }
                }
            }
        }

    private suspend inline fun <reified T> handeRequest(
        sendRequest: (accessToken: String) -> HttpResponse,
    ) = either {
        val accessToken = authRepository.requireAccessToken()
        val response = sendRequest(accessToken)
        ensure(response.status.isSuccess()) { response.toAppStatusCode() }
        response.body<T>()
    }
}