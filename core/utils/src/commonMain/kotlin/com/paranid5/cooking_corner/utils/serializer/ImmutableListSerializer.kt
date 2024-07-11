package com.paranid5.cooking_corner.utils.serializer

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class ImmutableListSerializer<T : Any>(private val elementSerializer: KSerializer<T>) :
    KSerializer<ImmutableList<T>> {

    override val descriptor: SerialDescriptor = ListSerializer(elementSerializer).descriptor

    override fun serialize(encoder: Encoder, value: ImmutableList<T>) {
        encoder.encodeSerializableValue(ListSerializer(elementSerializer), value.toList())
    }

    override fun deserialize(decoder: Decoder): ImmutableList<T> {
        val elements = decoder.decodeSerializableValue(ListSerializer(elementSerializer))
        return elements.toImmutableList()
    }
}