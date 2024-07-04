package com.paranid5.cooking_corner.data.di.storage

import com.paranid5.cooking_corner.data.auth.AuthDataSourceImpl
import com.paranid5.cooking_corner.data.di.AUTH_DATA_SOURCE
import com.paranid5.cooking_corner.domain.auth.AuthDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

internal val authDataSourceModule = DI.Module("authDataSourceModule") {
    bind<AuthDataSource>(tag = AUTH_DATA_SOURCE) with multiton { new(::AuthDataSourceImpl) }
}