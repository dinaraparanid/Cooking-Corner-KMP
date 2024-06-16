package com.paranid5.cooking_corner.feature.splash.di

import com.paranid5.cooking_corner.feature.splash.component.SplashScreenComponent
import com.paranid5.cooking_corner.feature.splash.component.SplashScreenComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val splashScreenModule = DI.Module("splashScreenModule") {
    bind<SplashScreenComponent.Factory>() with multiton { new(SplashScreenComponentImpl::Factory) }
}