package com.paranid5.cooking_corner.component.splash.di

import com.paranid5.cooking_corner.component.splash.SplashScreenComponent
import com.paranid5.cooking_corner.component.splash.SplashScreenComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

internal val splashScreenModule = DI.Module("splashScreenModule") {
    bind<SplashScreenComponent.Factory>() with multiton { new(SplashScreenComponentImpl::Factory) }
}