package com.paranid5.cooking_corner.utils

inline fun <T> Iterable<T>.span(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val first = mutableListOf<T>()
    val second = mutableListOf<T>()
    val iter = iterator()

    while (iter.hasNext()) {
        val isAdded = iter.next().takeIf { predicate(it).not() }?.let(first::add) ?: false
        if (isAdded.not()) break
    }

    while (iter.hasNext())
        second.add(iter.next())

    return first to second
}