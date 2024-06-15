package com.paranid5.cooking_corner.featrue.auth

import com.arkivanov.decompose.ComponentContext

interface AuthComponent {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): AuthComponent
    }
}