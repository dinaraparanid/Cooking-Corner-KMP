package com.paranid5.cooking_corner.data.di

import com.paranid5.cooking_corner.data.auth.AuthRepositoryImpl
import com.paranid5.cooking_corner.data.auth.TokenInteractorImpl
import com.paranid5.cooking_corner.data.category.CategoryRepositoryImpl
import com.paranid5.cooking_corner.data.di.network.networkModule
import com.paranid5.cooking_corner.data.di.storage.storageModule
import com.paranid5.cooking_corner.data.global_event.GlobalEventRepositoryImpl
import com.paranid5.cooking_corner.data.recipe.RecipeRepositoryImpl
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.TokenInteractor
import com.paranid5.cooking_corner.domain.category.CategoryRepository
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.multiton
import org.kodein.di.new

val dataModule = DI.Module("dataModule") {
    importAll(networkModule, storageModule)

    bind<GlobalEventRepository>() with multiton { new(::GlobalEventRepositoryImpl) }

    bind<TokenInteractor>() with multiton { new(::TokenInteractorImpl) }

    bind<AuthRepository>() with multiton {
        AuthRepositoryImpl(
            authApi = instance(tag = AUTH_API),
            authDataSource = instance(tag = AUTH_DATA_SOURCE),
        )
    }

    bind<RecipeRepository>() with multiton {
        RecipeRepositoryImpl(recipeApi = instance(tag = RECIPE_API))
    }

    bind<CategoryRepository>() with multiton {
        CategoryRepositoryImpl(categoryApi = instance(tag = CATEGORY_API))
    }
}