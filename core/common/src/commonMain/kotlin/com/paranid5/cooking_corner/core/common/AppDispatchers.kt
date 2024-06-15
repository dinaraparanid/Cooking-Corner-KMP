package com.paranid5.cooking_corner.core.common

import kotlinx.coroutines.CoroutineDispatcher

expect object AppDispatchers {
    val Ui: CoroutineDispatcher
    val Data: CoroutineDispatcher
    val Eval: CoroutineDispatcher
}