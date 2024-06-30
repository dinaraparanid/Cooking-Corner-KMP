package com.paranid5.cooking_corner.data.auth

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paranid5.cooking_corner.data.datastore.DataStoreClient
import com.paranid5.cooking_corner.domain.auth.AuthDataSource
import kotlinx.coroutines.flow.map

internal class AuthDataSourceImpl(private val dataStoreClient: DataStoreClient) : AuthDataSource {
    private companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override val accessTokenFlow by lazy {
        dataStoreClient
            .dataStore
            .data
            .map { preferences -> preferences[ACCESS_TOKEN] }
    }

    override val refreshTokenFlow by lazy {
        dataStoreClient
            .dataStore
            .data
            .map { preferences -> preferences[REFRESH_TOKEN] }
    }

    override suspend fun storeAccessToken(accessToken: String) {
        dataStoreClient.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun storeRefreshToken(refreshToken: String) {
        dataStoreClient.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }
}