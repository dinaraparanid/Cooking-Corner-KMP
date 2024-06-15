package com.paranid5.cooking_corner.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

internal interface DataStoreClient {
    val dataStore: DataStore<Preferences>
}