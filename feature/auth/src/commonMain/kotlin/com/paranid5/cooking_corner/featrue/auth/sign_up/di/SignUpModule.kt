package com.paranid5.cooking_corner.featrue.auth.sign_up.di

import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpComponent
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpComponentImpl
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStoreProvider
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

internal val signUpModule = DI.Module("signUpModule") {
    bind<SignUpStoreProvider.Factory>() with multiton { new(SignUpStoreProvider::Factory) }
    bind<SignUpComponent.Factory>() with multiton { new(SignUpComponentImpl::Factory) }
}