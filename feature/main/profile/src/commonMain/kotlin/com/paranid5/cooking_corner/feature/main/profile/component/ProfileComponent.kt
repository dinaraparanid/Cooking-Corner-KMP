package com.paranid5.cooking_corner.feature.main.profile.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import kotlinx.coroutines.flow.StateFlow

interface ProfileComponent : StateSource<ProfileState>, UiIntentHandler<ProfileUiIntent> {
    val childSlot: StateFlow<ChildSlot<*, ProfileChild>>

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): ProfileComponent
    }
}