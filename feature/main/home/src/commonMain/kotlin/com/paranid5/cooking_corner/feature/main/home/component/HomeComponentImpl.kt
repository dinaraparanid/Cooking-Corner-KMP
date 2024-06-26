package com.paranid5.cooking_corner.feature.main.home.component

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.paranid5.cooking_corner.component.getComponentStore
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.feature.main.home.component.HomeComponent.Child
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.Label
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.home.domain.RecipeUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable

internal class HomeComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: HomeStoreProvider.Factory,
    private val onBack: () -> Unit,
) : HomeComponent, ComponentContext by componentContext {
    @Serializable
    sealed interface Slot {
        @Serializable
        data class Recipe(val recipeUiState: RecipeUiState) : Slot
    }

    private val componentStore = getComponentStore(
        defaultState = State(),
        storeFactory = { storeFactory.create().provide(initialState = it) }
    )

    private val store = componentStore.value

    @OptIn(ExperimentalCoroutinesApi::class)
    override val stateFlow = store.stateFlow

    override val recepiesPagedFlow by lazy {
        // TODO: paging from network
        flowOf(
            PagingData.from(
                listOf(
                    RecipeUiState(
                        title = "Boiled shrimps",
                        rating = 4.8F,
                        preparingTime = 5,
                        cookingTime = 10,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Chicken in soy sauce",
                        rating = 4.5F,
                        preparingTime = 10,
                        cookingTime = 10,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Easy Lasagna in Pan",
                        rating = 4.1F,
                        preparingTime = 20,
                        cookingTime = 20,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Charlotte in a slow cooker",
                        rating = 4.9F,
                        preparingTime = 20,
                        cookingTime = 20,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Boiled shrimps",
                        rating = 4.8F,
                        preparingTime = 5,
                        cookingTime = 10,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Chicken in soy sauce",
                        rating = 4.5F,
                        preparingTime = 10,
                        cookingTime = 10,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Easy Lasagna in Pan",
                        rating = 4.1F,
                        preparingTime = 20,
                        cookingTime = 20,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Charlotte in a slow cooker",
                        rating = 4.9F,
                        preparingTime = 20,
                        cookingTime = 20,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Boiled shrimps",
                        rating = 4.8F,
                        preparingTime = 5,
                        cookingTime = 10,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Chicken in soy sauce",
                        rating = 4.5F,
                        preparingTime = 10,
                        cookingTime = 10,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Easy Lasagna in Pan",
                        rating = 4.1F,
                        preparingTime = 20,
                        cookingTime = 20,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Charlotte in a slow cooker",
                        rating = 4.9F,
                        preparingTime = 20,
                        cookingTime = 20,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Boiled shrimps",
                        rating = 4.8F,
                        preparingTime = 5,
                        cookingTime = 10,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Chicken in soy sauce",
                        rating = 4.5F,
                        preparingTime = 10,
                        cookingTime = 10,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Easy Lasagna in Pan",
                        rating = 4.1F,
                        preparingTime = 20,
                        cookingTime = 20,
                        author = "OlgaLove",
                    ),
                    RecipeUiState(
                        title = "Charlotte in a slow cooker",
                        rating = 4.9F,
                        preparingTime = 20,
                        cookingTime = 20,
                        author = "OlgaLove",
                    )
                )
            )
        )
    }

    private val childSlotNavigation = SlotNavigation<Slot>()

    override val childSlot: StateFlow<ChildSlot<*, Child>> = childSlot(
        source = childSlotNavigation,
        serializer = Slot.serializer(),
        childFactory = ::createChildSlot,
    ).toStateFlow()

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            store.labels bindTo ::onLabel
        }
    }

    override fun onUiIntent(intent: UiIntent) = store.accept(intent)

    private fun createChildSlot(
        configuration: Slot,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Slot.Recipe -> Child.Recepie
    }

    private fun onLabel(label: Label) = when (label) {
        is Label.ShowRecipe -> childSlotNavigation.activate(Slot.Recipe(label.recipeUiState))
    }

    class Factory(
        private val storeFactory: HomeStoreProvider.Factory,
    ) : HomeComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): HomeComponent = HomeComponentImpl(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onBack = onBack,
        )
    }
}