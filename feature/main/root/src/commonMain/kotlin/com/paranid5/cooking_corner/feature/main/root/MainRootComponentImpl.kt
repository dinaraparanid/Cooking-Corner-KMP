package com.paranid5.cooking_corner.feature.main.root

import com.arkivanov.decompose.ComponentContext

internal class MainRootComponentImpl(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : MainRootComponent, ComponentContext by componentContext {
    class Factory : MainRootComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): MainRootComponent = MainRootComponentImpl(
            componentContext = componentContext,
            onBack = onBack,
        )
    }
}