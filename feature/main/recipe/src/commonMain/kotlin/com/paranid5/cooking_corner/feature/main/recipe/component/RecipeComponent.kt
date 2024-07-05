package com.paranid5.cooking_corner.feature.main.recipe.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.coroutines.flow.StateFlow

interface RecipeComponent : StateSource<RecipeState>, UiIntentHandler<RecipeUiIntent> {
    val childSlot: StateFlow<ChildSlot<*, RecipeChild>>

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            recipeUiState: RecipeUiState,
            onBack: () -> Unit,
        ): RecipeComponent
    }
}