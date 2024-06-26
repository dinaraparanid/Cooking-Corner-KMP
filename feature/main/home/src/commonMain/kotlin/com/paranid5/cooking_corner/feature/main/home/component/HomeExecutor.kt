package com.paranid5.cooking_corner.feature.main.home.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.home.component.HomeStoreProvider.Msg

internal class HomeExecutor : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.ShowRecipe -> publish(Label.ShowRecipe(intent.recipeUiState))
            is UiIntent.UpdateSearchText -> dispatch(Msg.UpdateSearchText(intent.text))
            is UiIntent.SelectCategory -> dispatch(Msg.SelectCategory(intent.index))
            is UiIntent.AddRecipe -> Unit // TODO: Add recipe
            is UiIntent.DescendingFilterClick -> Unit // TODO: Descending filter
            is UiIntent.ShowFavourites -> Unit // TODO: Show favourites
            is UiIntent.LikeClick -> Unit // TODO: Like click
        }
    }
}