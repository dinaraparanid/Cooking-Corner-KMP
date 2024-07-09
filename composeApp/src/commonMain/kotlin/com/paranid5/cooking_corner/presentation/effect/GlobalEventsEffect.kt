package com.paranid5.cooking_corner.presentation.effect

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.token_expired
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent
import com.paranid5.cooking_corner.domain.global_event.isError
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.utils.doNothing
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GlobalEventsEffect(
    globalEventState: State<GlobalEvent?>,
    snackbarJobState: MutableState<Job?>,
    snackbarHostState: SnackbarHostState,
) {
    val globalEvent by globalEventState
    val coroutineScope = rememberCoroutineScope()

    when (val event = globalEvent) {
        is GlobalEvent.LogOut -> if (event.isError) {
            val message = logOutSnackbarMessage()
            snackbarJobState.value = coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }

        is GlobalEvent.ShowSnackbar -> {
            snackbarJobState.value = coroutineScope.launch {
                snackbarHostState.showSnackbar(event.message)
            }
        }

        null -> doNothing
    }
}

@Composable
private fun logOutSnackbarMessage() = SnackbarMessage(
    message = stringResource(Res.string.token_expired),
    snackbarType = SnackbarType.NEGATIVE,
    withDismissAction = true,
)