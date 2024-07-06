package com.paranid5.cooking_corner.feature.main.generate.di

import com.paranid5.cooking_corner.feature.main.generate.component.GenerateComponent
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val generateModule = DI.Module("generateModule") {
    bind<GenerateComponent.Factory>() with multiton { new(GenerateComponentImpl::Factory) }
}