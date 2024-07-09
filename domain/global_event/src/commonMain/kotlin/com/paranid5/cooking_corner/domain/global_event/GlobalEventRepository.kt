package com.paranid5.cooking_corner.domain.global_event

import kotlinx.coroutines.flow.Flow

interface GlobalEventRepository {
    val globalEventFlow: Flow<GlobalEvent>

    suspend fun sendEvent(globalEvent: GlobalEvent)
}