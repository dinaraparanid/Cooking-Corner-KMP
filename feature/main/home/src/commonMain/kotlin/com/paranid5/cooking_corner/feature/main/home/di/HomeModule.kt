package com.paranid5.cooking_corner.feature.main.home.di

import com.paranid5.cooking_corner.feature.main.home.component.HomeComponent
import com.paranid5.cooking_corner.feature.main.home.component.HomeComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val homeModule = DI.Module("homeModule") {
    bind<HomeComponent.Factory>() with multiton { new(HomeComponentImpl::Factory) }
}