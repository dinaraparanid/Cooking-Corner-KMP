package com.paranid5.cooking_corner.domain.recipe

import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeModifyParams
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse

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

    suspend fun create(recipeModifyParams: RecipeModifyParams): ApiResultWithCode<Unit>

    suspend fun update(
        recipeId: Long,
        recipeModifyParams: RecipeModifyParams,
    ): ApiResultWithCode<Unit>

    suspend fun publish(recipeId: Long): ApiResultWithCode<Unit>
}