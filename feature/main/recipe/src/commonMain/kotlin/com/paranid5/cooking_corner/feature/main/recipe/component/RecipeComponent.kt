package com.paranid5.cooking_corner.feature.main.recipe.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler

interface RecipeComponent : StateSource<RecipeState>, UiIntentHandler<RecipeUiIntent> {
    sealed interface BackResult {
        data object Dismiss : BackResult
        data class Edit(val recipeId: Long) : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            recipeId: Long,
            onBack: (BackResult) -> Unit,
        ): RecipeComponent
    }
}