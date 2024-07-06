package com.paranid5.cooking_corner.data.auth

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.paranid5.cooking_corner.data.datastore.DataStoreClient
import com.paranid5.cooking_corner.domain.auth.AuthDataSource
import kotlinx.coroutines.flow.map

internal class AuthDataSourceImpl(private val dataStoreClient: DataStoreClient) : AuthDataSource {
    private companion object {
        private val LOGIN_KEY = stringPreferencesKey("login")
        private val PASSWORD_KEY = stringPreferencesKey("password")

        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    override val loginFlow by lazy {
        dataStoreClient
            .dataStore
            .data
            .map { preferences -> preferences[LOGIN_KEY] }
    }

    override val passwordFlow by lazy {
        dataStoreClient
            .dataStore
            .data
            .map { preferences -> preferences[PASSWORD_KEY] }
    }

    override val accessTokenFlow by lazy {
        dataStoreClient
            .dataStore
            .data
            .map { preferences -> preferences[ACCESS_TOKEN_KEY] }
    }

    override val refreshTokenFlow by lazy {
        dataStoreClient
            .dataStore
            .data
            .map { preferences -> preferences[REFRESH_TOKEN_KEY] }
    }

    override suspend fun storeLogin(login: String) {
        dataStoreClient.dataStore.edit { preferences ->
            preferences[LOGIN_KEY] = login
        }
    }

    override suspend fun storePassword(password: String) {
        dataStoreClient.dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = password
        }
    }

    override suspend fun storeAccessToken(accessToken: String) {
        dataStoreClient.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    override suspend fun storeRefreshToken(refreshToken: String) {
        dataStoreClient.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }
}