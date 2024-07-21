package com.paranid5.cooking_corner.data.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath

internal class DataStoreClientImpl : DataStoreClient {
    private companion object {
        const val DATA_STORE_PATH = "CookingCorner/params.preferences_pb"
    }

    private val dataStorePath by lazy {
        "${System.getProperty("user.home").orEmpty()}/$DATA_STORE_PATH"
    }

    override val dataStore by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { dataStorePath.toPath() }
        )
    }
}