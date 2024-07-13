package com.paranid5.cooking_corner.data.global_event

import com.paranid5.cooking_corner.domain.global_event.GlobalEvent
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal class GlobalEventRepositoryImpl : GlobalEventRepository {
    private val _globalEventFlow =
        MutableSharedFlow<GlobalEvent>()

    override val globalEventFlow =
        _globalEventFlow.asSharedFlow()

    override suspend fun sendEvent(globalEvent: GlobalEvent) =
        _globalEventFlow.emit(globalEvent)
}
