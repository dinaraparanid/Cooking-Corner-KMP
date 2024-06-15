package com.paranid5.cooking_corner.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

internal class DataStoreClientImpl(context: Context) : DataStoreClient {
    private val Context.dataStore by preferencesDataStore("params")
    override val dataStore = context.dataStore
}