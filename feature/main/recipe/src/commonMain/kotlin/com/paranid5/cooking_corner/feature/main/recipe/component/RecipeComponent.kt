package com.paranid5.cooking_corner.feature.main.recipe.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.ui.entity.RecipeUiState

interface RecipeComponent : StateSource<RecipeState>, UiIntentHandler<RecipeUiIntent> {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            recipeUiState: RecipeUiState,
            onBack: () -> Unit,
        ): RecipeComponent
    }
}