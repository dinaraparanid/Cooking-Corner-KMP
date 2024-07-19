package com.paranid5.cooking_corner.data.recipe

internal class RecipeApiUrlBuilder(private val baseUrl: String) {
    fun buildGetRecentRecipesUrl() = "$baseUrl/recipes/get_recent_recipes"

    fun buildGetBestRatedRecipesUrl() = "$baseUrl/recipes/get_best_rated"

    fun buildMyRecipesUrl() = "$baseUrl/recipes/get_my_recipes"

    fun buildAddToMyRecipesUrl() =
        "$baseUrl/recipes/add_to_my_recipes"

    fun buildRemoveFromMyRecipesUrl() =
        "$baseUrl/recipes/delete_from_my_recipes"

    fun buildAddToFavouritesUrl() =
        "$baseUrl/recipes/add_to_favourites"

    fun buildRemoveFromFavouritesUrl() =
        "$baseUrl/recipes/remove_from_favourites"

    fun buildGetRecipeByIdUrl(recipeId: Long) =
        "$baseUrl/recipes/get_by_id/$recipeId"

    fun buildGetRecipeByNameUrl() =
        "$baseUrl/recipes/get_by_name"

    fun buildCreateUrl() = "$baseUrl/recipes/create"

    fun buildUpdateUrl() = "$baseUrl/recipes/update"

    fun buildPublishUrl(recipeId: Long) = "$baseUrl/recipes/publish/$recipeId"

    fun buildRateUrl() = "$baseUrl/recipes/rate_recipe"

    fun buildGenerateRecipeUrl() = "$baseUrl/recipes/generate_recipe"

    fun buildUploadRecipeImageUrl() = "$baseUrl/recipes/upload_recipe_image"
}
