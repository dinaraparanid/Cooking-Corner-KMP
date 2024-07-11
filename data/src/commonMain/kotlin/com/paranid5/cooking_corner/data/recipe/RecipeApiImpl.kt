package com.paranid5.cooking_corner.data.recipe

import arrow.core.Either
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.data.auth.withAuth
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeApi
import com.paranid5.cooking_corner.domain.recipe.dto.MyRecipesRequest
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.withContext

internal class RecipeApiImpl(
    private val ktorClient: HttpClient,
    private val urlBuilder: RecipeApiUrlBuilder,
    private val authRepository: AuthRepository,
) : RecipeApi {
    override suspend fun getRecentRecipes(): ApiResultWithCode<List<RecipeResponse>> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.get(urlBuilder.buildGetRecentRecipesUrl()) {
                        bearerAuth(accessToken)
                    }
                }
            }
        }

    override suspend fun getBestRatedRecipes(): ApiResultWithCode<List<RecipeResponse>> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.get(urlBuilder.buildGetBestRatedRecipesUrl()) {
                        bearerAuth(accessToken)
                    }
                }
            }
        }

    override suspend fun getMyRecipes(
        categoryName: String,
        isFavourite: Boolean,
        ascendingOrder: Boolean,
    ): ApiResultWithCode<List<RecipeResponse>> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.post(urlBuilder.buildMyRecipesUrl()) {
                    bearerAuth(accessToken)
                    contentType(ContentType.Application.Json)

                    setBody(
                        MyRecipesRequest(
                            categoryName = categoryName,
                            isFavourite = isFavourite,
                            ascendingOrder = ascendingOrder,
                        )
                    )
                }
            }
        }
    }

    override suspend fun addToFavourites(recipeId: Long): ApiResultWithCode<Unit> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.post(urlBuilder.buildAddToFavouritesUrl(recipeId = recipeId)) {
                    bearerAuth(accessToken)
                }
            }
        }
    }

    override suspend fun removeFromFavourites(recipeId: Long): ApiResultWithCode<Unit> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.delete(
                        urlBuilder.buildRemoveFromFavouritesUrl(recipeId = recipeId)
                    ) {
                        bearerAuth(accessToken)
                    }
                }
            }
        }

    override suspend fun addToMyRecipes(recipeId: Long): ApiResultWithCode<Unit> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.post(urlBuilder.buildAddToMyRecipesUrl(recipeId = recipeId)) {
                    bearerAuth(accessToken)
                }
            }
        }
    }

    override suspend fun removeFromMyRecipes(recipeId: Long): ApiResultWithCode<Unit> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.delete(urlBuilder.buildRemoveFromMyRecipesUrl(recipeId = recipeId)) {
                        bearerAuth(accessToken)
                    }
                }
            }
        }

    override suspend fun getRecipeById(recipeId: Long): ApiResultWithCode<RecipeResponse> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.get(urlBuilder.buildGetRecipeByIdUrl(recipeId = recipeId)) {
                        bearerAuth(accessToken)
                    }
                }
            }
        }
}
