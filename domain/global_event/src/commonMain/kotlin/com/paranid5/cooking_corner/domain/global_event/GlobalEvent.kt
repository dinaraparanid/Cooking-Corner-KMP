package com.paranid5.cooking_corner.domain.global_event

import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage

sealed interface GlobalEvent {
    data class LogOut(val reason: Reason) : GlobalEvent {
        enum class Reason { MANUAL, ERROR }
    }

    data class ShowSnackbar(val message: SnackbarMessage) : GlobalEvent
}

val GlobalEvent.LogOut.isError
    get() = reason == GlobalEvent.LogOut.Reason.ERROR