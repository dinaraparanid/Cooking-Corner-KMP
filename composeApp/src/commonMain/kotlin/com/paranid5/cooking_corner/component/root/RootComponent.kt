package com.paranid5.cooking_corner.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import kotlinx.coroutines.flow.StateFlow

interface RootComponent :
    ComponentContext,
    StateSource<RootState>,
    UiIntentHandler<RootUiIntent> {
    val stack: StateFlow<ChildStack<RootConfig, RootChild>>

    interface Factory {
        fun create(componentContext: ComponentContext): RootComponent
    }
}