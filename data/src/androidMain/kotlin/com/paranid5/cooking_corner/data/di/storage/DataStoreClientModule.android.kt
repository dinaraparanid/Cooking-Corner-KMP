package com.paranid5.cooking_corner.data.di.storage

import com.paranid5.cooking_corner.data.datastore.DataStoreClient
import com.paranid5.cooking_corner.data.datastore.DataStoreClientImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

internal actual val dataStoreClientModule = DI.Module("dataStoreClientModule") {
    bind<DataStoreClient>() with multiton { new(::DataStoreClientImpl) }
}