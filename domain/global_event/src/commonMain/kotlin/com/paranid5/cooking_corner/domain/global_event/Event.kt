package com.paranid5.cooking_corner.domain.global_event

sealed interface Event {
    data object LogOut : Event
}