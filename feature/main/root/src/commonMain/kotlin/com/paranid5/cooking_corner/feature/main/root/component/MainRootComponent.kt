package com.paranid5.cooking_corner.feature.main.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow

interface MainRootComponent {
    val stack: StateFlow<ChildStack<MainRootConfig, MainRootChild>>

    enum class AuthorizeType {
        SIGNED_IN, SIGNED_UP
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            authType: AuthorizeType,
            onBack: () -> Unit,
        ): MainRootComponent
    }
}