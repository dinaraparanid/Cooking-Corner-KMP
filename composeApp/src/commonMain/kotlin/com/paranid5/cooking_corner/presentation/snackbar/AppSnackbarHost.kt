package com.paranid5.cooking_corner.presentation.snackbar

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.presentation.effect.GlobalEventsEffect
import kotlinx.coroutines.Job

@Composable
internal fun AppSnackbarHost(
    globalEventState: State<GlobalEvent?>,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarJobState = remember { mutableStateOf<Job?>(null) }
    var snackbarJob by snackbarJobState

    val dismissSnackbar = {
        snackbarJob?.cancel()
        snackbarJob = null
    }

    GlobalEventsEffect(
        globalEventState = globalEventState,
        snackbarJobState = snackbarJobState,
        snackbarHostState = snackbarHostState,
    )

    SnackbarHost(
        modifier = modifier,
        hostState = snackbarHostState,
    ) { snackbarData ->
        val snackbarVisuals = snackbarData.visuals as SnackbarMessage

        AppSnackbar(
            message = snackbarVisuals.message,
            actionLabel = snackbarVisuals.actionLabel,
            snackbarType = snackbarVisuals.snackbarType,
            gravity = snackbarVisuals.gravity,
            onDismiss = dismissSnackbar.takeIf { snackbarVisuals.withDismissAction },
            onAction = snackbarData::performAction,
        )
    }
}