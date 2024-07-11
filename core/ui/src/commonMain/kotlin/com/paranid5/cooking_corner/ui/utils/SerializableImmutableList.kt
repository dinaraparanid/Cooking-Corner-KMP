package com.paranid5.cooking_corner.ui.utils

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.utils.serializer.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class SerializableImmutableList<T>(
    @Serializable(with = ImmutableListSerializer::class)
    val value: ImmutableList<T>
) {
    constructor() : this(value = persistentListOf())
}