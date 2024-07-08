package com.paranid5.cooking_corner.domain.global_event

sealed interface Event {
    data class LogOut(val reason: Reason) : Event {
        enum class Reason { MANUAL, ERROR }
    }
}