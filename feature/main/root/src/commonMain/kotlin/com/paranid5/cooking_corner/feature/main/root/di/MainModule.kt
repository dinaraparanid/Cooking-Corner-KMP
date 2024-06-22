package com.paranid5.cooking_corner.feature.main.root.di

import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponent
import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponentImpl
import com.paranid5.cooking_corner.feature.main.splash.di.mainSplashScreenModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val mainModule = DI.Module("mainRootModule") {
    importAll(mainSplashScreenModule)
    bind<MainRootComponent.Factory>() with multiton { new(MainRootComponentImpl::Factory) }
}