package com.paranid5.cooking_corner.component

import com.arkivanov.decompose.ComponentContext

interface UiIntentHandler<UiIntent> : ComponentContext {
    fun onUiIntent(intent: UiIntent)
}