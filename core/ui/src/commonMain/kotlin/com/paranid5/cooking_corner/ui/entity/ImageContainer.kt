package com.paranid5.cooking_corner.ui.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Immutable
sealed interface ImageContainer {
    @Serializable
    @Immutable
    data class Uri(val value: String?) : ImageContainer

    @Serializable
    @Immutable
    data class Bytes(@Transient val value: ByteArray? = null) : ImageContainer
}

inline val ImageContainer.data: Any?
    get() = when (this) {
        is ImageContainer.Bytes -> value
        is ImageContainer.Uri -> value
    }