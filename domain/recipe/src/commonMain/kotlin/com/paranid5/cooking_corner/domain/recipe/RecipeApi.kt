package com.paranid5.cooking_corner.domain.recipe

import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeModifyParams
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.recipe.dto.UploadCoverResponse

interface RecipeApi {
    suspend fun getRecentRecipes(): ApiResultWithCode<List<RecipeResponse>>

    suspend fun getBestRatedRecipes(): ApiResultWithCode<List<RecipeResponse>>

    suspend fun getMyRecipes(
        categoryName: String = "",
        isFavourite: Boolean = false,
        ascendingOrder: Boolean = false,
    ): ApiResultWithCode<List<RecipeResponse>>

    suspend fun addToMyRecipes(recipeId: Long): ApiResultWithCode<Unit>

    suspend fun removeFromMyRecipes(recipeId: Long): ApiResultWithCode<Unit>

    suspend fun addToFavourites(recipeId: Long): ApiResultWithCode<Unit>

    suspend fun removeFromFavourites(recipeId: Long): ApiResultWithCode<Unit>

    suspend fun getRecipeById(recipeId: Long): ApiResultWithCode<RecipeResponse>

    suspend fun getRecipesByName(name: String): ApiResultWithCode<List<RecipeResponse>>

    suspend fun create(recipeModifyParams: RecipeModifyParams): ApiResultWithCode<Unit>

    suspend fun update(
        recipeId: Long,
        recipeModifyParams: RecipeModifyParams,
    ): ApiResultWithCode<Unit>

    suspend fun publish(recipeId: Long): ApiResultWithCode<Unit>

    suspend fun rate(recipeId: Long, rating: Int): ApiResultWithCode<Unit>

    suspend fun generate(url: String): ApiResultWithCode<RecipeResponse>

    suspend fun uploadCover(cover: ByteArray): ApiResultWithCode<UploadCoverResponse>
}
