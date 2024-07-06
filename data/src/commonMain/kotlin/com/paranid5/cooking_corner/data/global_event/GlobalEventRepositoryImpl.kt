package com.paranid5.cooking_corner.data.global_event

import com.paranid5.cooking_corner.domain.global_event.Event
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class GlobalEventRepositoryImpl : GlobalEventRepository {
    private val _eventFlow = MutableSharedFlow<Event>()

    override val eventFlow = _eventFlow.asSharedFlow()

    override suspend fun sendEvent(event: Event) = _eventFlow.emit(event)
}