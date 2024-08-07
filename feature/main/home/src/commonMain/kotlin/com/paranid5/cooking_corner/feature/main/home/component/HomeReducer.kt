package com.paranid5.cooking_corner.feature.main.home.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStoreProvider.Msg

internal object HomeReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg): State = when (msg) {
        is Msg.UpdateSearchText -> copy(searchText = msg.text)
        is Msg.UpdateOrder -> copy(isAscendingOrder = isAscendingOrder.not())
        is Msg.UpdateFavouritesShown -> copy(areFavouritesShown = areFavouritesShown.not())
        is Msg.SelectCategory -> copy(selectedCategoryIndex = msg.index)
        is Msg.UpdateRecipesUiState -> copy(recipesUiState = msg.recipesUiState)
        is Msg.UpdateCategoriesUiState -> copy(categoriesUiState = msg.categoriesUiState)
    }
}