package com.paranid5.cooking_corner.domain.global_event

import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import kotlin.random.Random

sealed interface GlobalEvent {
    data class LogOut(
        val reason: Reason,
        private val id: Long = Random.nextLong(),
    ) : GlobalEvent {
        enum class Reason { MANUAL, ERROR }
    }

    data class ShowSnackbar(
        val message: SnackbarMessage,
        private val id: Long = Random.nextLong(),
    ) : GlobalEvent
}

val GlobalEvent.LogOut.isError
    get() = reason == GlobalEvent.LogOut.Reason.ERROR