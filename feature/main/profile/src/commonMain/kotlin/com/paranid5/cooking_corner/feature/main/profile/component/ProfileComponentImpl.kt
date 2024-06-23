package com.paranid5.cooking_corner.feature.main.profile.component

import com.arkivanov.decompose.ComponentContext

internal class ProfileComponentImpl(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : ProfileComponent, ComponentContext by componentContext {
    class Factory : ProfileComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): ProfileComponent = ProfileComponentImpl(
            componentContext = componentContext,
            onBack = onBack,
        )
    }
}