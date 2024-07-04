package com.paranid5.cooking_corner.data.di

import com.paranid5.cooking_corner.data.auth.AuthRepositoryImpl
import com.paranid5.cooking_corner.data.di.network.networkModule
import com.paranid5.cooking_corner.data.di.storage.storageModule
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.multiton

val dataModule = DI.Module("dataModule") {
    importAll(networkModule, storageModule)

    bind<AuthRepository>() with multiton {
        AuthRepositoryImpl(
            authApi = instance(tag = AUTH_API),
            authDataSource = instance(tag = AUTH_DATA_SOURCE),
        )
    }
}