package com.paranid5.cooking_corner.feature.main.search.component

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.paranid5.cooking_corner.component.getComponentStore
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent
import com.paranid5.cooking_corner.feature.main.search.component.SearchComponent.BackResult
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.Label
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.State
import com.paranid5.cooking_corner.feature.main.search.component.SearchStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable

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

    override val lastRecepiesPagedFlow by lazy {
        // TODO: paging from network
        stub()
    }

    override val recommendedRecepiesPagedFlow by lazy {
        // TODO: paging from network
        stub()
    }

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            store.labels bindTo ::onLabel
        }
    }

    override fun onUiIntent(intent: UiIntent) = store.accept(intent)

    private fun onLabel(label: Label) = when (label) {
        is Label.ShowRecipe -> onBack(
            BackResult.ShowRecipeDetails(recipeUiState = label.recipeUiState)
        )
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

private fun stub() = flowOf(
    PagingData.from(
        listOf(
            RecipeUiState(
                title = "Boiled shrimps",
                rating = 4.8F,
                preparingTime = 5,
                cookingTime = 10,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Chicken in soy sauce",
                rating = 4.5F,
                preparingTime = 10,
                cookingTime = 10,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Easy Lasagna in Pan",
                rating = 4.1F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Charlotte in a slow cooker",
                rating = 4.9F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Boiled shrimps",
                rating = 4.8F,
                preparingTime = 5,
                cookingTime = 10,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Chicken in soy sauce",
                rating = 4.5F,
                preparingTime = 10,
                cookingTime = 10,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Easy Lasagna in Pan",
                rating = 4.1F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Charlotte in a slow cooker",
                rating = 4.9F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Boiled shrimps",
                rating = 4.8F,
                preparingTime = 5,
                cookingTime = 10,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Chicken in soy sauce",
                rating = 4.5F,
                preparingTime = 10,
                cookingTime = 10,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Easy Lasagna in Pan",
                rating = 4.1F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Charlotte in a slow cooker",
                rating = 4.9F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Boiled shrimps",
                rating = 4.8F,
                preparingTime = 5,
                cookingTime = 10,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Chicken in soy sauce",
                rating = 4.5F,
                preparingTime = 10,
                cookingTime = 10,
                isLiked = false,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Easy Lasagna in Pan",
                rating = 4.1F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = true,
                author = "OlgaLove",
            ),
            RecipeUiState(
                title = "Charlotte in a slow cooker",
                rating = 4.9F,
                preparingTime = 20,
                cookingTime = 20,
                isLiked = false,
                author = "OlgaLove",
            ),
        )
    )
)