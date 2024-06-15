package com.paranid5.cooking_corner.featrue.auth.di

import com.paranid5.cooking_corner.featrue.auth.AuthComponent
import com.paranid5.cooking_corner.featrue.auth.AuthComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val authModule = DI.Module("authModule") {
    bind<AuthComponent.Factory>() with multiton { new(AuthComponentImpl::Factory) }
}