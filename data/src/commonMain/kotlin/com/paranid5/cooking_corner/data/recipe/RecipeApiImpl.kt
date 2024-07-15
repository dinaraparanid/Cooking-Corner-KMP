package com.paranid5.cooking_corner.data.recipe

import arrow.core.Either
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.data.auth.withAuth
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeApi
import com.paranid5.cooking_corner.domain.recipe.dto.CreateRecipeRequest
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeModifyParams
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.recipe.dto.SearchRecipesRequest
import com.paranid5.cooking_corner.domain.recipe.dto.UpdateRecipeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.withContext

internal class RecipeApiImpl(
    private val ktorClient: HttpClient,
    private val urlBuilder: RecipeApiUrlBuilder,
    private val authRepository: AuthRepository,
) : RecipeApi {
    private companion object {
        const val RECIPE_ID_QUERY = "recipe_id"
        const val NAME_QUERY = "name"
        const val RATING_QUERY = "rating"
        const val URL_QUERY = "url"
    }

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
                        SearchRecipesRequest(
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
                ktorClient.post(urlBuilder.buildAddToFavouritesUrl()) {
                    url { parameter(RECIPE_ID_QUERY, recipeId) }
                    bearerAuth(accessToken)
                }
            }
        }
    }

    override suspend fun removeFromFavourites(recipeId: Long): ApiResultWithCode<Unit> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.delete(urlBuilder.buildRemoveFromFavouritesUrl()) {
                        url { parameter(RECIPE_ID_QUERY, recipeId) }
                        bearerAuth(accessToken)
                    }
                }
            }
        }

    override suspend fun addToMyRecipes(recipeId: Long): ApiResultWithCode<Unit> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.post(urlBuilder.buildAddToMyRecipesUrl()) {
                    url { parameter(RECIPE_ID_QUERY, recipeId) }
                    bearerAuth(accessToken)
                }
            }
        }
    }

    override suspend fun removeFromMyRecipes(recipeId: Long): ApiResultWithCode<Unit> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.delete(urlBuilder.buildRemoveFromMyRecipesUrl()) {
                        url { parameter(RECIPE_ID_QUERY, recipeId) }
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

    override suspend fun getRecipesByName(name: String): ApiResultWithCode<List<RecipeResponse>> =
        Either.catch {
            authRepository
                .withAuth<List<RecipeResponse>?> { accessToken ->
                    withContext(AppDispatchers.Data) {
                        ktorClient.post(urlBuilder.buildGetRecipeByNameUrl()) {
                            url { parameter(NAME_QUERY, name) }
                            bearerAuth(accessToken)
                            contentType(ContentType.Application.Json)
                            setBody(SearchRecipesRequest())
                        }
                    }
                }
                .map { it.orEmpty() }
        }

    override suspend fun create(recipeModifyParams: RecipeModifyParams): ApiResultWithCode<Unit> =
        Either.catch {
            authRepository.withAuth { accessToken ->
                withContext(AppDispatchers.Data) {
                    ktorClient.post(urlBuilder.buildCreateUrl()) {
                        bearerAuth(accessToken)
                        contentType(ContentType.Application.Json)
                        setBody(
                            recipeModifyParams.run {
                                CreateRecipeRequest(
                                    name = recipeModifyParams.name,
                                    description = description,
                                    iconPath = iconPath,
                                    category = category,
                                    preparingTime = preparingTime,
                                    cookingTime = cookingTime,
                                    waitingTime = waitingTime,
                                    totalTime = totalTime,
                                    ingredients = ingredients,
                                    steps = steps,
                                    portions = portions,
                                    comments = comments,
                                    nutritions = nutritions,
                                    proteins = proteins,
                                    fats = fats,
                                    carbohydrates = carbohydrates,
                                    dishes = dishes,
                                    videoLink = videoLink,
                                    source = source,
                                )
                            }
                        )
                    }
                }
            }
        }

    override suspend fun update(
        recipeId: Long,
        recipeModifyParams: RecipeModifyParams,
    ): ApiResultWithCode<Unit> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.put(urlBuilder.buildUpdateUrl()) {
                    bearerAuth(accessToken)
                    contentType(ContentType.Application.Json)
                    setBody(
                        recipeModifyParams.run {
                            UpdateRecipeRequest(
                                id = recipeId,
                                name = recipeModifyParams.name,
                                description = description,
                                iconPath = iconPath,
                                category = category,
                                preparingTime = preparingTime,
                                cookingTime = cookingTime,
                                waitingTime = waitingTime,
                                totalTime = totalTime,
                                ingredients = ingredients,
                                steps = steps,
                                portions = portions,
                                comments = comments,
                                nutritions = nutritions,
                                proteins = proteins,
                                fats = fats,
                                carbohydrates = carbohydrates,
                                dishes = dishes,
                                videoLink = videoLink,
                                source = source,
                            )
                        }
                    )
                }
            }
        }
    }

    override suspend fun publish(recipeId: Long): ApiResultWithCode<Unit> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.put(urlBuilder.buildPublishUrl(recipeId)) {
                    bearerAuth(accessToken)
                }
            }
        }
    }

    override suspend fun rate(recipeId: Long, rating: Int): ApiResultWithCode<Unit> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.post(urlBuilder.buildRateUrl()) {
                    url {
                        parameter(RECIPE_ID_QUERY, recipeId)
                        parameter(RATING_QUERY, rating)
                    }

                    bearerAuth(accessToken)
                }
            }
        }
    }

    override suspend fun generate(url: String): ApiResultWithCode<RecipeResponse> = Either.catch {
        authRepository.withAuth { accessToken ->
            withContext(AppDispatchers.Data) {
                ktorClient.get(urlBuilder.buildGenerateRecipeUrl()) {
                    url { parameter(URL_QUERY, url) }
                    bearerAuth(accessToken)
                }
            }
        }
    }
}
