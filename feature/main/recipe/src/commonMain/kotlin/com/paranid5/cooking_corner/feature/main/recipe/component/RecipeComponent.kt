package com.paranid5.cooking_corner.feature.main.recipe.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.ui.entity.RecipeUiState

interface RecipeComponent : StateSource<RecipeState>, UiIntentHandler<RecipeUiIntent> {
    sealed interface BackResult {
        data object Dismiss : BackResult
        data object Edit : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            recipeUiState: RecipeUiState,
            onBack: (BackResult) -> Unit,
        ): RecipeComponent
    }
}