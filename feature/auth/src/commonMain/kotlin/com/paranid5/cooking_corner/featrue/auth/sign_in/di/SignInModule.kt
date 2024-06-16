package com.paranid5.cooking_corner.featrue.auth.sign_in.di

import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInComponent
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInComponentImpl
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStoreProvider
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

internal val signInModule = DI.Module("signInModule") {
    bind<SignInStoreProvider.Factory>() with multiton { new(SignInStoreProvider::Factory) }
    bind<SignInComponent.Factory>() with multiton { new(SignInComponentImpl::Factory) }
}