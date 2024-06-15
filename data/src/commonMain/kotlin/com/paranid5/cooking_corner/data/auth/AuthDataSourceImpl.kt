package com.paranid5.cooking_corner.data.auth

import com.paranid5.cooking_corner.data.datastore.DataStoreClient
import com.paranid5.cooking_corner.domain.auth.AuthDataSource

internal class AuthDataSourceImpl(private val dataStoreClient: DataStoreClient) : AuthDataSource