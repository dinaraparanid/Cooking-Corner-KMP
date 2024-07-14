package com.paranid5.cooking_corner.data.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal actual fun KtorClient() = HttpClient(OkHttp) {
    install(UserAgent) {
        agent = USER_AGENT
    }

    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }

    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL
    }

    install(HttpTimeout) {
        requestTimeoutMillis = TIMEOUT_MS
        connectTimeoutMillis = TIMEOUT_MS
        socketTimeoutMillis = TIMEOUT_MS
    }
}