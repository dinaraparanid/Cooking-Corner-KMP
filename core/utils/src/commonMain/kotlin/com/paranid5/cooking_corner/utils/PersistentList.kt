package com.paranid5.cooking_corner.utils

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

fun <T> persistentListOfNotNull(vararg elements: T?): PersistentList<T> =
    listOfNotNull(*elements).toPersistentList()