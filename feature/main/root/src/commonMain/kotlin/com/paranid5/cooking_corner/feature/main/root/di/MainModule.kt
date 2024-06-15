package com.paranid5.cooking_corner.feature.main.root.di

import com.paranid5.cooking_corner.feature.main.root.MainRootComponent
import com.paranid5.cooking_corner.feature.main.root.MainRootComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val mainModule = DI.Module("mainRootModule") {
    bind<MainRootComponent.Factory>() with multiton { new(MainRootComponentImpl::Factory) }
}