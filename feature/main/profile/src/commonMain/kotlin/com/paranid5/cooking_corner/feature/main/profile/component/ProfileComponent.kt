package com.paranid5.cooking_corner.feature.main.profile.component

import com.arkivanov.decompose.ComponentContext

interface ProfileComponent {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): ProfileComponent
    }
}