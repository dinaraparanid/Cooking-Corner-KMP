package com.paranid5.cooking_corner.data.recipe

internal class RecipeApiUrlBuilder(private val baseUrl: String) {
    fun buildGetRecentRecipesUrl() = "$baseUrl/recipes/get_recent_recipes"

    fun buildGetBestRatedRecipesUrl() = "$baseUrl/recipes/get_best_rated"

    fun buildMyRecipesUrl() = "$baseUrl/recipes/get_my_recipes"
}