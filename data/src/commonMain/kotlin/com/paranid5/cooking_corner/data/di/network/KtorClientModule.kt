package com.paranid5.cooking_corner.data.di.network

import com.paranid5.cooking_corner.data.ktor.KtorClient
import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

internal val ktorClientModule = DI.Module("ktorClientModule") {
    bind<HttpClient>() with singleton { KtorClient() }
}