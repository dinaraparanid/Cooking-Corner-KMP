package com.paranid5.cooking_corner.feature.main.home.component

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    val childSlot: StateFlow<ChildSlot<*, Child>>

    val recepiesPagedFlow: Flow<PagingData<RecipeUiState>>

    sealed interface Child {
        class RecepieDetails internal constructor(internal val component: RecipeComponent) : Child
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): HomeComponent
    }
}