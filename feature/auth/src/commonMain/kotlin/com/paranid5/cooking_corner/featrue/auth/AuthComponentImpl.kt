package com.paranid5.cooking_corner.featrue.auth

import com.arkivanov.decompose.ComponentContext

internal class AuthComponentImpl(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : AuthComponent, ComponentContext by componentContext {
    class Factory : AuthComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): AuthComponent = AuthComponentImpl(
            componentContext = componentContext,
            onBack = onBack,
        )
    }
}