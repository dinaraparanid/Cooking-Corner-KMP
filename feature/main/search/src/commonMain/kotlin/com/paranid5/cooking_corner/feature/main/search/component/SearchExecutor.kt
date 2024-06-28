package com.paranid5.cooking_corner.feature.main.search.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.feature.main.search.component.SearchStoreProvider.Msg

internal class SearchExecutor : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) = when (intent) {
        is UiIntent.AddToRecipesClick -> Unit // TODO: Add to recipes
        is UiIntent.ShowRecipe -> publish(Label.ShowRecipe(intent.recipeUiState))
        is UiIntent.UpdateSearchText -> dispatch(Msg.UpdateSearchText(intent.text))
    }
}