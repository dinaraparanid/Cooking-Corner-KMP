package com.paranid5.cooking_corner.domain.recipe

import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.domain.recipe.entity.RecipeResponse

interface RecipeApi {
    suspend fun getRecentRecipes(): ApiResultWithCode<List<RecipeResponse>>

    suspend fun getBestRatedRecipes(): ApiResultWithCode<List<RecipeResponse>>

    suspend fun getMyRecipes(): ApiResultWithCode<List<RecipeResponse>>
}