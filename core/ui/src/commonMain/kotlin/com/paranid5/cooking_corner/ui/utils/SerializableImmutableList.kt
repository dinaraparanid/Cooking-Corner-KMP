package com.paranid5.cooking_corner.ui.utils

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.utils.serializer.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class SerializableImmutableList<T>(
    @Serializable(with = ImmutableListSerializer::class)
    private val value: ImmutableList<T>
) : ImmutableList<T> by value {
    constructor() : this(value = persistentListOf())
    constructor(list: List<T>) : this(value = list.toImmutableList())
}
