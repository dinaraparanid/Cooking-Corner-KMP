package com.paranid5.cooking_corner.feature.main.content.di

import com.paranid5.cooking_corner.feature.main.content.component.MainContentComponent
import com.paranid5.cooking_corner.feature.main.content.component.MainContentComponentImpl
import com.paranid5.cooking_corner.feature.main.home.di.homeModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val mainContentModule = DI.Module("mainContentModule") {
    importAll(homeModule)
    bind<MainContentComponent.Factory>() with multiton { new(MainContentComponentImpl::Factory) }
}