package com.paranid5.cooking_corner.feature.main.root

import com.arkivanov.decompose.ComponentContext

interface MainRootComponent {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): MainRootComponent
    }
}