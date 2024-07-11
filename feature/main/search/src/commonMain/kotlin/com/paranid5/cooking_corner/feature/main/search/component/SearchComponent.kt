package com.paranid5.cooking_corner.feature.main.search.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent

interface SearchComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    sealed interface BackResult {
        data object Dismiss : BackResult
        data class ShowRecipeDetails(val recipeId: Long) : BackResult
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): SearchComponent
    }
}