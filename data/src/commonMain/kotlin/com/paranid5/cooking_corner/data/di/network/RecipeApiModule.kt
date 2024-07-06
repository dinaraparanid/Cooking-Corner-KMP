package com.paranid5.cooking_corner.data.di.network

import com.paranid5.cooking_corner.data.di.BASE_URL_QUALIFIER
import com.paranid5.cooking_corner.data.di.RECIPE_API
import com.paranid5.cooking_corner.data.recipe.RecipeApiImpl
import com.paranid5.cooking_corner.data.recipe.RecipeApiUrlBuilder
import com.paranid5.cooking_corner.domain.recipe.RecipeApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.multiton
import org.kodein.di.new

internal val recipeApiModule = DI.Module("recipeApiModule") {
    bind<RecipeApi>(tag = RECIPE_API) with multiton { new(::RecipeApiImpl) }

    bind<RecipeApiUrlBuilder>() with multiton {
        RecipeApiUrlBuilder(baseUrl = instance(tag = BASE_URL_QUALIFIER))
    }
}
