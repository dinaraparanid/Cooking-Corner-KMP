package com.paranid5.cooking_corner.data.di.network

import com.paranid5.cooking_corner.data.auth.AuthApiImpl
import com.paranid5.cooking_corner.data.auth.AuthApiUrlBuilder
import com.paranid5.cooking_corner.data.di.AUTH_API
import com.paranid5.cooking_corner.data.di.BASE_URL_QUALIFIER
import com.paranid5.cooking_corner.domain.auth.AuthApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.multiton
import org.kodein.di.new

internal val authApiModule = DI.Module("authApiModule") {
    bind<AuthApi>(tag = AUTH_API) with multiton { new(::AuthApiImpl) }

    bind<AuthApiUrlBuilder>() with multiton {
        AuthApiUrlBuilder(baseUrl = instance(tag = BASE_URL_QUALIFIER))
    }
}
