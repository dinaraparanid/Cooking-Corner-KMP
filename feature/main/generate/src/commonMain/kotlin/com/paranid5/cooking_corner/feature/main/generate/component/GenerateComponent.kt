package com.paranid5.cooking_corner.feature.main.generate.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler

interface GenerateComponent : StateSource<GenerateState>, UiIntentHandler<GenerateUiIntent> {
    sealed interface BackResult {
        data object Generated : BackResult

        data object Dismiss : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (result: BackResult) -> Unit,
        ): GenerateComponent
    }
}