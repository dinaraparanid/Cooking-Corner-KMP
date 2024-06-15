package com.paranid5.cooking_corner.data.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath

internal class DataStoreClientImpl : DataStoreClient {
    private companion object {
        const val DATA_STORE_PATH = "params.preferences_pb"
    }

    val dataStore by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { DATA_STORE_PATH.toPath() }
        )
    }
}