package com.paranid5.cooking_corner.domain.global_event

import kotlinx.coroutines.flow.Flow

interface GlobalEventRepository {
    val eventFlow: Flow<Event>

    suspend fun sendEvent(event: Event)
}