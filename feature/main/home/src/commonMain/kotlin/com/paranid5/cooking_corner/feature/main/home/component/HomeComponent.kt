package com.paranid5.cooking_corner.feature.main.home.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent

interface HomeComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    sealed interface BackResult {
        data object Dismiss : BackResult
        data class ShowRecipeDetails(val recipeId: Long) : BackResult
        data object ShowAddRecipe : BackResult
        data object ShowImportRecipe : BackResult
        data class ShowRecipeEditor(val recipeId: Long) : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): HomeComponent
    }
}