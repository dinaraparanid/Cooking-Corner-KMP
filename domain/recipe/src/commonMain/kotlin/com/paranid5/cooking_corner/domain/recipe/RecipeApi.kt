package com.paranid5.cooking_corner.domain.recipe

import com.paranid5.cooking_corner.core.common.ApiResultWithCode
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

    suspend fun deleteFromMyRecipes(recipeId: Long): ApiResultWithCode<Unit>

    suspend fun addToFavourites(recipeId: Long): ApiResultWithCode<Unit>

    suspend fun removeFromFavourites(recipeId: Long): ApiResultWithCode<Unit>
}