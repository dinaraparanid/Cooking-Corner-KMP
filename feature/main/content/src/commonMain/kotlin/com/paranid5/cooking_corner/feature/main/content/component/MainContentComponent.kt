package com.paranid5.cooking_corner.feature.main.content.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.component.UiIntentHandler
import kotlinx.coroutines.flow.StateFlow

interface MainContentComponent : UiIntentHandler<MainContentUiIntent> {
    val stack: StateFlow<ChildStack<MainContentConfig, MainContentChild>>

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): MainContentComponent
    }
}