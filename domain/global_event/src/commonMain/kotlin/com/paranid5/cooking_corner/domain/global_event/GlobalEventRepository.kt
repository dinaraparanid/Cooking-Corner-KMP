package com.paranid5.cooking_corner.domain.global_event

import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import kotlinx.coroutines.flow.Flow

interface GlobalEventRepository {
    val globalEventFlow: Flow<GlobalEvent>

    suspend fun sendEvent(globalEvent: GlobalEvent)
}

suspend fun GlobalEventRepository.sendSnackbar(snackbarMessage: SnackbarMessage) =
    sendEvent(GlobalEvent.ShowSnackbar(snackbarMessage))

suspend fun GlobalEventRepository.sendLogOut(reason: Reason) =
    sendEvent(GlobalEvent.LogOut(reason))
