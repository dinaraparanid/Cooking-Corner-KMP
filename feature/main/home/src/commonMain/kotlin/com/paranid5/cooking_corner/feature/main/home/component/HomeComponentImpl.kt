package com.paranid5.cooking_corner.feature.main.home.component

import com.arkivanov.decompose.ComponentContext

internal class HomeComponentImpl(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : HomeComponent, ComponentContext by componentContext {
    class Factory : HomeComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit
        ): HomeComponent = HomeComponentImpl(
            componentContext = componentContext,
            onBack = onBack,
        )
    }
}