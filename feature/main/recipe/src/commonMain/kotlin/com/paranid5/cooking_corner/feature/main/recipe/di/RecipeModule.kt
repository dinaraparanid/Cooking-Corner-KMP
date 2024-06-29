package com.paranid5.cooking_corner.feature.main.recipe.di

import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val recipeModule = DI.Module("recipeModule") {
    bind<RecipeComponent.Factory>() with multiton { new(RecipeComponentImpl::Factory) }
}