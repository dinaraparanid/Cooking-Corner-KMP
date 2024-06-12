package com.paranid5.cooking_corner.component

import kotlinx.serialization.json.Json

internal val json = Json {
    allowStructuredMapKeys = true
    ignoreUnknownKeys = true
}