package com.paranid5.cooking_corner.data.di.network

import com.paranid5.cooking_corner.data.di.BASE_URL_QUALIFIER
import com.paranid5.cooking_corner.data.ktor.BASE_URL
import com.paranid5.cooking_corner.data.ktor.KtorClient
import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

internal val ktorClientModule = DI.Module("ktorClientModule") {
    bind<String>(tag = BASE_URL_QUALIFIER) with singleton { BASE_URL }
    bind<HttpClient>() with singleton { KtorClient() }
}