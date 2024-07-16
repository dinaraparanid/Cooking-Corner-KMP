package com.paranid5.cooking_corner.feature.main.profile.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler

interface ProfileComponent : StateSource<ProfileState>, UiIntentHandler<ProfileUiIntent> {
    sealed interface BackResult {
        data object Dismiss : BackResult
        data object Edit : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): ProfileComponent
    }
}