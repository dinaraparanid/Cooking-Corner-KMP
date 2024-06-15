package com.paranid5.cooking_corner.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual object AppDispatchers {
    actual val Ui: CoroutineDispatcher = Dispatchers.Main
    actual val Data: CoroutineDispatcher = Dispatchers.IO
    actual val Eval: CoroutineDispatcher = Dispatchers.Default
}