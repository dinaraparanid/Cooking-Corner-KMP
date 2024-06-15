package com.paranid5.cooking_corner.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {
    val stack: StateFlow<ChildStack<RootConfig, RootChild>>

    interface Factory {
        fun create(componentContext: ComponentContext): RootComponent
    }
}