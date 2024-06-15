package com.paranid5.cooking_corner.data.di.network

import com.paranid5.cooking_corner.data.auth.AuthApiImpl
import com.paranid5.cooking_corner.domain.auth.AuthApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

internal val authApiModule = DI.Module("authApiModule") {
    bind<AuthApi>() with multiton { new(::AuthApiImpl) }
}