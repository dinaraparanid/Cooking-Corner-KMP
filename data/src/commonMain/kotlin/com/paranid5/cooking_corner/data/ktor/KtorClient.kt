package com.paranid5.cooking_corner.data.ktor

import io.ktor.client.HttpClient

internal const val USER_AGENT =
    "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.98 Safari/537.36"

internal const val TIMEOUT_MS = 20_000L

internal expect fun KtorClient(): HttpClient
