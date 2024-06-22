package com.paranid5.cooking_corner.feature.main.home.component

import com.arkivanov.decompose.ComponentContext

interface HomeComponent {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): HomeComponent
    }
}