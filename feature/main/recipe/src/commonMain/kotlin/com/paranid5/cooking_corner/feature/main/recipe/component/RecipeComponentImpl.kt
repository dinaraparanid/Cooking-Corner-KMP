package com.paranid5.cooking_corner.feature.main.recipe.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.getComponentState
import com.paranid5.cooking_corner.feature.main.recipe.entity.IngredientUiState
import com.paranid5.cooking_corner.feature.main.recipe.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.feature.main.recipe.entity.StepUiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class RecipeComponentImpl(
    componentContext: ComponentContext,
    recipeUiState: RecipeUiState,
    private val onBack: () -> Unit,
) : RecipeComponent, ComponentContext by componentContext {
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

    override fun onUiIntent(intent: RecipeUiIntent) {
        when (intent) {
            is RecipeUiIntent.Back -> onBack()
            is RecipeUiIntent.Edit -> Unit // TODO: Edit click
        }
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
)

private fun stepsStub() = persistentListOf(
    StepUiState(text = stepStub()),
    StepUiState(text = stepStub()),
    StepUiState(text = stepStub()),
    StepUiState(text = stepStub()),
    StepUiState(text = stepStub()),
)

private fun ingredientsStub() = persistentListOf(
    IngredientUiState(title = "Shrimps"),
    IngredientUiState(title = "Shrimps"),
    IngredientUiState(title = "Shrimps"),
    IngredientUiState(title = "Shrimps"),
    IngredientUiState(title = "Shrimps"),
)

private fun stepStub() = "Prepare the necessary ingredients. The shrimp should be approximately the same size, whole, the ice coat should not be thick, otherwise the shrimp will lose a lot of weight during the defrosting process. Typically, shrimp on sale are already pre-boiled, so they cook quite quickly."
