package com.paranid5.cooking_corner.feature.main.recipe.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.paranid5.cooking_corner.component.getComponentState
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.feature.main.recipe.entity.IngredientUiState
import com.paranid5.cooking_corner.feature.main.recipe.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.feature.main.recipe.entity.StepUiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.utils.updateState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

internal class RecipeComponentImpl(
    componentContext: ComponentContext,
    recipeUiState: RecipeUiState,
    private val onBack: () -> Unit,
) : RecipeComponent, ComponentContext by componentContext {
    @Serializable
    sealed interface Slot {
        @Serializable
        data object Edit : Slot
    }

    private val componentState = getComponentState(
        defaultState = RecipeState(
            // TODO: acquire from server
            recipe = recipeStub(recipeUiState),
            steps = stepsStub(),
            ingredients = ingredientsStub(),
        )
    )

    private val _stateFlow = MutableStateFlow(componentState.value)
    override val stateFlow = _stateFlow.asStateFlow()

    private val childSlotNavigation = SlotNavigation<Slot>()

    override val childSlot: StateFlow<ChildSlot<*, RecipeChild>> = childSlot(
        source = childSlotNavigation,
        serializer = Slot.serializer(),
        childFactory = ::createChildSlot,
    ).toStateFlow()

    private fun createChildSlot(
        configuration: Slot,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Slot.Edit -> RecipeChild.Edit
    }

    override fun onUiIntent(intent: RecipeUiIntent) {
        when (intent) {
            is RecipeUiIntent.Back -> onBack()

            is RecipeUiIntent.ChangeKebabMenuVisibility ->
                changeKebabMenuVisibility(isVisible = intent.isVisible)

            is RecipeUiIntent.Edit -> childSlotNavigation.activate(Slot.Edit)

            is RecipeUiIntent.Publish -> publishRecipe()

            is RecipeUiIntent.Delete -> deleteRecipe()
        }
    }

    private fun changeKebabMenuVisibility(isVisible: Boolean) =
        _stateFlow.updateState { copy(isKebabMenuVisible = isVisible) }

    private fun publishRecipe() {
        // TODO: publish request
    }

    private fun deleteRecipe() {
        // TODO: delete recipe
    }

    class Factory : RecipeComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            recipeUiState: RecipeUiState,
            onBack: () -> Unit,
        ): RecipeComponent = RecipeComponentImpl(
            componentContext = componentContext,
            recipeUiState = recipeUiState,
            onBack = onBack,
        )
    }
}

private fun recipeStub(recipeUiState: RecipeUiState) = RecipeDetailedUiState(
    title = recipeUiState.title,
    rating = recipeUiState.rating,
    preparingTime = recipeUiState.preparingTime,
    cookingTime = recipeUiState.cookingTime,
    author = recipeUiState.author,
    isLiked = recipeUiState.isLiked,
    reviews = 15000,
    portions = 5,
    byUser = true,
    isPublished = false,
)

private fun stepsStub() = persistentListOf(
    StepUiState(text = stepStub()),
    StepUiState(text = stepStub()),
    StepUiState(text = stepStub()),
    StepUiState(text = stepStub()),
    StepUiState(text = stepStub()),
)

private fun ingredientsStub() = persistentListOf(
    IngredientUiState(title = "Shrimps", portion = "1 kg"),
    IngredientUiState(title = "Water", portion = "2 l"),
    IngredientUiState(title = "Salt", portion = "90 g"),
    IngredientUiState(title = "Dill", portion = "10 g"),
    IngredientUiState(title = "Parsley", portion = "10 g"),
    IngredientUiState(title = "Allspice", portion = "5 whispers"),
    IngredientUiState(title = "Peppercorns", portion = "15 pieces"),
)

private fun stepStub() = "Prepare the necessary ingredients. The shrimp should be approximately the same size, whole, the ice coat should not be thick, otherwise the shrimp will lose a lot of weight during the defrosting process. Typically, shrimp on sale are already pre-boiled, so they cook quite quickly."
