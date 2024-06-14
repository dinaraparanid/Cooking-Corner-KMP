package com.paranid5.cooking_corner.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T> MutableStateFlow<T>.updateState(transofrm: T.() -> T) = update { transofrm(it) }