package com.paranid5.cooking_corner.feature.main.splash.di

import com.paranid5.cooking_corner.feature.main.splash.component.MainSplashScreenComponent
import com.paranid5.cooking_corner.feature.main.splash.component.MainSplashScreenComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val mainSplashScreenModule = DI.Module("mainSplashScreenModule") {
    bind<MainSplashScreenComponent.Factory>() with multiton { new(MainSplashScreenComponentImpl::Factory) }
}