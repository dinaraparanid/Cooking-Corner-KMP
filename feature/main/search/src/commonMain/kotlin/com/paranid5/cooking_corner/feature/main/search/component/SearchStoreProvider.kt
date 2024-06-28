package com.paranid5.cooking_corner.feature.main.search.component

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent

internal class SearchStoreProvider(private val storeFactory: StoreFactory) {
    sealed interface Msg {
        data class UpdateSearchText(val text: String) : Msg
    }

    fun provide(initialState: SearchStore.State): SearchStore = object :
        SearchStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = initialState,
            executorFactory = ::SearchExecutor,
            reducer = SearchReducer,
        ) {}

    class Factory(private val storeFactory: StoreFactory) {
        fun create() = SearchStoreProvider(storeFactory)
    }
}