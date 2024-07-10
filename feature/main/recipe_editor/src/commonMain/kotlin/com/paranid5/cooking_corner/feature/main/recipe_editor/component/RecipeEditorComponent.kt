package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent

interface RecipeEditorComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    sealed interface BackResult {
        data object Dismiss : BackResult
        data object Uploaded : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): RecipeEditorComponent
    }
}