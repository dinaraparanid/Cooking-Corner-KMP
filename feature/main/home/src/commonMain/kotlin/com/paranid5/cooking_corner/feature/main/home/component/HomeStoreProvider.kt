package com.paranid5.cooking_corner.feature.main.home.component

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.collections.immutable.ImmutableList

internal class HomeStoreProvider(
    private val storeFactory: StoreFactory,
    private val recipeRepository: RecipeRepository,
    private val globalEventRepository: GlobalEventRepository,
) {
    sealed interface Msg {
        data class UpdateSearchText(val text: String) : Msg
        data class SelectCategory(val index: Int) : Msg
        data class UpdateRecipes(val recipes: ImmutableList<RecipeUiState>) : Msg
        data class UpdateUiState(val uiState: UiState<Unit>) : Msg
    }

    fun provide(initialState: State): HomeStore = object :
        HomeStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = initialState,
            executorFactory = {
                HomeExecutor(
                    recipeRepository = recipeRepository,
                    globalEventRepository = globalEventRepository,
                )
            },
            reducer = HomeReducer,
            bootstrapper = SimpleBootstrapper(Unit),
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val recipeRepository: RecipeRepository,
        private val globalEventRepository: GlobalEventRepository,
    ) {
        fun create() = HomeStoreProvider(
            storeFactory = storeFactory,
            recipeRepository = recipeRepository,
            globalEventRepository = globalEventRepository,
        )
    }
}
