package com.paranid5.cooking_corner.featrue.auth.di

import com.paranid5.cooking_corner.featrue.auth.component.AuthComponent
import com.paranid5.cooking_corner.featrue.auth.component.AuthComponentImpl
import com.paranid5.cooking_corner.featrue.auth.sign_in.di.signInModule
import com.paranid5.cooking_corner.featrue.auth.sign_up.di.signUpModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val authModule = DI.Module("authModule") {
    importAll(signInModule, signUpModule)
    bind<AuthComponent.Factory>() with multiton { new(AuthComponentImpl::Factory) }
}