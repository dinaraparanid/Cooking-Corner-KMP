package com.paranid5.cooking_corner.utils

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

fun <T> persistentListOfNotNull(vararg elements: T?): PersistentList<T> =
    listOfNotNull(*elements).toPersistentList()

infix fun <T> ImmutableList<T>.with(element: T): ImmutableList<T> =
    (this + element).toImmutableList()

infix fun <T> ImmutableList<T>.without(element: T): ImmutableList<T> =
    (this - element).toImmutableList()