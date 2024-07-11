package com.paranid5.cooking_corner.utils

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlin.experimental.ExperimentalTypeInference

fun <T> persistentListOfNotNull(vararg elements: T?): PersistentList<T> =
    listOfNotNull(*elements).toPersistentList()

infix fun <T> ImmutableList<T>.with(element: T): ImmutableList<T> =
    (this + element).toImmutableList()

infix fun <T> ImmutableList<T>.without(element: T): ImmutableList<T> =
    (this - element).toImmutableList()

inline fun <T, R> Iterable<T>.mapToImmutableList(transform: (T) -> R): ImmutableList<R> =
    map(transform).toImmutableList()

inline fun <T> Iterable<T>.filterToImmutableList(predicate: (T) -> Boolean): ImmutableList<T> =
    filter(predicate).toImmutableList()

fun <T> ImmutableList<T>?.orNil(): ImmutableList<T> = this ?: persistentListOf()

@OptIn(ExperimentalTypeInference::class)
inline fun <T> buildPersistentList(@BuilderInference builderAction: MutableList<T>.() -> Unit) =
    mutableListOf<T>().apply(builderAction).toPersistentList()
