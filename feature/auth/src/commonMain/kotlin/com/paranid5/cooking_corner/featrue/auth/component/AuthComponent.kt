package com.paranid5.cooking_corner.featrue.auth.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.component.UiIntentHandler
import kotlinx.coroutines.flow.StateFlow

interface AuthComponent : UiIntentHandler<AuthUiIntent> {
    val stack: StateFlow<ChildStack<AuthConfig, AuthChild>>

    sealed interface BackResult {
        data object Dismiss : BackResult
        data object Authorized : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): AuthComponent
    }
}