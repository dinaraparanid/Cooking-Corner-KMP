package com.paranid5.cooking_corner.utils

fun String?.toIntOrZero() = this?.toIntOrNull() ?: 0

fun Number?.toStringOrEmpty() = this?.toString() ?: ""