package com.paranid5.cooking_corner.data.recipe

internal class RecipeApiUrlBuilder(private val baseUrl: String) {
    fun buildGetRecentRecipesUrl() = "$baseUrl/recipes/get_recent_recipes"

    fun buildGetBestRatedRecipesUrl() = "$baseUrl/recipes/get_best_rated"

    fun buildMyRecipesUrl() = "$baseUrl/recipes/get_my_recipes"

    fun buildAddToMyRecipesUrl(recipeId: Long) =
        "$baseUrl/recipes/add_to_my_recipes?recipe_id=$recipeId"

    fun buildRemoveFromMyRecipesUrl(recipeId: Long) =
        "$baseUrl/recipes/delete_from_my_recipes?recipe_id=$recipeId"

    fun buildAddToFavouritesUrl(recipeId: Long) =
        "$baseUrl/recipes/add_to_favourites?recipe_id=$recipeId"

    fun buildRemoveFromFavouritesUrl(recipeId: Long) =
        "$baseUrl/recipes/remove_from_favourites?recipe_id=$recipeId"

    fun buildGetRecipeByIdUrl(recipeId: Long) =
        "$baseUrl/recipes/get_by_id/$recipeId"

    fun buildCreateUrl() = "$baseUrl/recipes/create"

    fun buildUpdateUrl() = "$baseUrl/recipes/update"
}