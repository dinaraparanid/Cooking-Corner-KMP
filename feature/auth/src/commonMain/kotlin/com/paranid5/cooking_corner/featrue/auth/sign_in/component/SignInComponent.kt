package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent

internal interface SignInComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    sealed interface BackResult {
        data object SignedIn : BackResult
        data object ShowSignUp : BackResult
        data object Dismiss : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): SignInComponent
    }
}