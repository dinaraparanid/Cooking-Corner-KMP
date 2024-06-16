package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent

internal interface SignUpComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    sealed interface BackResult {
        data object SignedUp : BackResult
        data object Dismiss : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): SignUpComponent
    }
}