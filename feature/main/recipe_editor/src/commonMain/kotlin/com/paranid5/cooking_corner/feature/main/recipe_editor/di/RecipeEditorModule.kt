package com.paranid5.cooking_corner.feature.main.recipe_editor.di

import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponentImpl
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStoreProvider
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val recipeEditorModule = DI.Module("recipeEditorModule") {
    bind<RecipeEditorComponent.Factory>() with multiton { new(RecipeEditorComponentImpl::Factory) }
    bind<RecipeEditorStoreProvider.Factory>() with multiton { new(RecipeEditorStoreProvider::Factory) }
}