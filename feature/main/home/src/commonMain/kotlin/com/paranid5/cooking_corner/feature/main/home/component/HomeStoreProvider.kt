package com.paranid5.cooking_corner.feature.main.home.component

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent

internal class HomeStoreProvider(private val storeFactory: StoreFactory) {
    sealed interface Msg {
        data class UpdateSearchText(val text: String) : Msg
    }

    fun provide(initialState: State): HomeStore = object :
        HomeStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = initialState,
            executorFactory = ::HomeExecutor,
            reducer = HomeReducer,
        ) {}

    class Factory(private val storeFactory: StoreFactory) {
        fun create() = HomeStoreProvider(storeFactory)
    }
}