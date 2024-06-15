package com.paranid5.cooking_corner.data.di

import com.paranid5.cooking_corner.data.di.network.networkModule
import com.paranid5.cooking_corner.data.di.storage.storageModule
import org.kodein.di.DI

val dataModule = DI.Module("dataModule") {
    importAll(networkModule, storageModule)
}