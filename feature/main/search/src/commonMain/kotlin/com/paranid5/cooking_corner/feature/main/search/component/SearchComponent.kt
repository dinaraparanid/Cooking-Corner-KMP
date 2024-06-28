package com.paranid5.cooking_corner.feature.main.search.component

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    val childSlot: StateFlow<ChildSlot<*, Child>>

    val lastRecepiesPagedFlow: Flow<PagingData<RecipeUiState>>
    val recommendedRecepiesPagedFlow: Flow<PagingData<RecipeUiState>>

    sealed interface Child {
        data object Recepie : Child
    }

    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): SearchComponent
    }
}