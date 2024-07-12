package com.paranid5.cooking_corner.domain.recipe

import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.domain.recipe.dto.IngredientDTO
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.recipe.dto.StepDTO

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

    suspend fun create(
        name: String,
        description: String?,
        iconPath: String?,
        category: String?,
        tag: String?,
        preparingTime: Int?,
        cookingTime: Int?,
        waitingTime: Int?,
        totalTime: Int?,
        ingredients: List<IngredientDTO>,
        steps: List<StepDTO>,
        portions: Int?,
        comments: String?,
        nutritions: Int?,
        proteins: Int?,
        fats: Int?,
        carbohydrates: Int?,
        dishes: Int?,
        videoLink: String?,
        source: String?,
    ): ApiResultWithCode<Unit>
}