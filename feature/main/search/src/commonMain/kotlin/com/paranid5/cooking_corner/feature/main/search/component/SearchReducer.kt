package com.paranid5.cooking_corner.feature.main.search.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStoreProvider.Msg

internal object SearchReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg) = when (msg) {
        is Msg.UpdateSearchText -> copy(searchText = msg.text)
        is Msg.UpdateBestRatedRecipes -> copy(bestRatedRecipes = msg.recipes)
        is Msg.UpdateRecentRecipes -> copy(recentRecipes = msg.recipes)
        is Msg.UpdateUiState -> copy(uiState = msg.uiState)
    }
}