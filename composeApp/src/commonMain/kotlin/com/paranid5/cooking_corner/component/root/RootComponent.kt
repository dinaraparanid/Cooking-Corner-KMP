package com.paranid5.cooking_corner.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RootComponent : StateSource<RootState> {
    val stack: StateFlow<ChildStack<*, RootChild>>
    val globalGlobalEventFlow: Flow<GlobalEvent>

    interface Factory {
        fun create(componentContext: ComponentContext): RootComponent
    }
}