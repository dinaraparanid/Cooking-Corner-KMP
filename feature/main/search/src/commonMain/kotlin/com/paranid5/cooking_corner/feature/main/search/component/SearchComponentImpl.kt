package com.paranid5.cooking_corner.feature.main.search.component

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.paranid5.cooking_corner.component.componentScope
import com.paranid5.cooking_corner.component.getComponentStore
import com.paranid5.cooking_corner.feature.main.search.component.SearchComponent.BackResult
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class SearchComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: SearchStoreProvider.Factory,
    private val onBack: (BackResult) -> Unit,
) : SearchComponent, ComponentContext by componentContext {
    private val componentStore = getComponentStore(
        defaultState = State(),
        storeFactory = { storeFactory.create().provide(initialState = it) }
    )

    private val store = componentStore.value

    @OptIn(ExperimentalCoroutinesApi::class)
    override val stateFlow = store.stateFlow

    private var subscribeStateJob: Job? = null

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            store.labels bindTo ::onLabel
        }

        doOnResume {
            subscribeStateJob = subscribeOnStateUpdates()
        }

        doOnPause {
            subscribeStateJob?.cancel()
            subscribeStateJob = null
        }
    }

    override fun onUiIntent(intent: UiIntent) = store.accept(intent)

    private fun onLabel(label: Label) = when (label) {
        is Label.ShowRecipe -> onBack(
            BackResult.ShowRecipeDetails(recipeUiState = label.recipeUiState)
        )
    }

    private fun subscribeOnStateUpdates() = componentScope.launch {
        stateFlow
            .map { it.searchText }
            .distinctUntilChanged()
            .collectLatest { searchText ->
                val intent = when {
                    searchText.isBlank() -> UiIntent.LoadRecipes
                    else -> UiIntent.SearchRecipes
                }

                onUiIntent(intent)
            }
    }

    class Factory(private val storeFactory: SearchStoreProvider.Factory) : SearchComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): SearchComponent = SearchComponentImpl(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onBack = onBack,
        )
    }
}
