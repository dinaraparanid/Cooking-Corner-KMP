package com.paranid5.cooking_corner.di

import com.paranid5.cooking_corner.component.di.storeFactoryModule
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.component.root.RootComponentImpl
import com.paranid5.cooking_corner.data.di.dataModule
import com.paranid5.cooking_corner.featrue.auth.di.authModule
import com.paranid5.cooking_corner.feature.main.root.di.mainModule
import com.paranid5.cooking_corner.feature.splash.di.splashScreenModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val appModule = DI.Module("appModule") {
    importAll(
        dataModule,
        storeFactoryModule,
        splashScreenModule,
        authModule,
        mainModule,
    )

    bind<RootComponent.Factory>() with multiton { new(RootComponentImpl::Factory) }
}