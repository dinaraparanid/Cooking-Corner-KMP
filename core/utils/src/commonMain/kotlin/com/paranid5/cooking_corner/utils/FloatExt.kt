package com.paranid5.cooking_corner.utils

fun Float.withPrecision(precision: Int): String {
    val (intPart, floatPart) = toString().split('.')
    return "$intPart.${floatPart.take(precision)}"
}