package com.paranid5.cooking_corner.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.StateFlow

interface StateSource<State> : ComponentContext {
    val stateFlow: StateFlow<State>
}