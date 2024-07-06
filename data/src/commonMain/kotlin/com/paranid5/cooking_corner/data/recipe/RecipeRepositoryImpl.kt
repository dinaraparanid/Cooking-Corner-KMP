package com.paranid5.cooking_corner.data.recipe

import com.paranid5.cooking_corner.domain.recipe.RecipeApi
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository

internal class RecipeRepositoryImpl(recipeApi: RecipeApi) :
    RecipeRepository,
    RecipeApi by recipeApi