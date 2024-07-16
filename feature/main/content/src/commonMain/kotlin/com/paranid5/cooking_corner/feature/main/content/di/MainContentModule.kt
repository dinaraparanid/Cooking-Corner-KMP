package com.paranid5.cooking_corner.feature.main.content.di

import com.paranid5.cooking_corner.feature.main.content.component.MainContentComponent
import com.paranid5.cooking_corner.feature.main.content.component.MainContentComponentImpl
import com.paranid5.cooking_corner.feature.main.generate.di.generateModule
import com.paranid5.cooking_corner.feature.main.home.di.homeModule
import com.paranid5.cooking_corner.feature.main.profile.di.profileModule
import com.paranid5.cooking_corner.feature.main.profile_editor.di.profileEditorModule
import com.paranid5.cooking_corner.feature.main.recipe.di.recipeModule
import com.paranid5.cooking_corner.feature.main.recipe_editor.di.recipeEditorModule
import com.paranid5.cooking_corner.feature.main.search.di.searchModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val mainContentModule = DI.Module("mainContentModule") {
    importAll(
        searchModule,
        homeModule,
        profileModule,
        recipeModule,
        generateModule,
        recipeEditorModule,
        profileEditorModule,
    )

    bind<MainContentComponent.Factory>() with multiton { new(MainContentComponentImpl::Factory) }
}
