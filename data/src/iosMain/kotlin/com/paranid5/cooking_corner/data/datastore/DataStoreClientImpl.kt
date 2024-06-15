package com.paranid5.cooking_corner.data.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal class DataStoreClientImpl : DataStoreClient {
    private companion object {
        const val DATA_STORE_PATH = "params.preferences_pb"

        @OptIn(ExperimentalForeignApi::class)
        inline val datastoreUrl: String
            get() {
                val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )

                return "${requireNotNull(documentDirectory).path}/$DATA_STORE_PATH"
            }
    }

    override val dataStore by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { datastoreUrl.toPath() }
        )
    }
}