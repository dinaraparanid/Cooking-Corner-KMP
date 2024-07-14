package com.paranid5.cooking_corner.feature.main.search.component

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList

internal class SearchStoreProvider(
    private val storeFactory: StoreFactory,
    private val recipeRepository: RecipeRepository,
    private val globalEventRepository: GlobalEventRepository,
) {
    sealed interface Msg {
        data class UpdateSearchText(val text: String) : Msg

        data object CancelSearching : Msg

        data class UpdateRecentRecipes(
            val recipes: SerializableImmutableList<RecipeUiState>
        ) : Msg

        data class UpdateBestRatedRecipes(
            val recipes: SerializableImmutableList<RecipeUiState>
        ) : Msg

        data class UpdateFoundRecipesUiState(
            val recipesUiState: UiState<SerializableImmutableList<RecipeUiState>>
        ) : Msg

        data class UpdatePreviewUiState(val uiState: UiState<Unit>) : Msg
    }

    fun provide(initialState: State): SearchStore = object :
        SearchStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "SearchStore",
            initialState = initialState,
            executorFactory = {
                SearchExecutor(
                    recipeRepository = recipeRepository,
                    globalEventRepository = globalEventRepository,
                )
            },
            reducer = SearchReducer,
            bootstrapper = SimpleBootstrapper(Unit),
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val recipeRepository: RecipeRepository,
        private val globalEventRepository: GlobalEventRepository,
    ) {
        fun create() = SearchStoreProvider(
            storeFactory = storeFactory,
            recipeRepository = recipeRepository,
            globalEventRepository = globalEventRepository,
        )
    }
}