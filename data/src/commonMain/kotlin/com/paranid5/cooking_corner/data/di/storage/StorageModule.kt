package com.paranid5.cooking_corner.data.di.storage

import org.kodein.di.DI

internal val storageModule = DI.Module("storageModule") {
    importAll(
        dataStoreClientModule,
        authDataSourceModule,
    )
}