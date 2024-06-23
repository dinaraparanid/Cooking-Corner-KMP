package com.paranid5.cooking_corner.feature.main.profile.di

import com.paranid5.cooking_corner.feature.main.profile.component.ProfileComponent
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileComponentImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val profileModule = DI.Module("profileModule") {
    bind<ProfileComponent.Factory>() with multiton { new(ProfileComponentImpl::Factory) }
}