package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStoreProvider.Msg
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.with
import com.paranid5.cooking_corner.utils.without

internal object RecipeEditorReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg): State = when (msg) {
        is Msg.UpdateCarbohydrates -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(carbohydratesInput = msg.carbohydratesInput)
        )

        is Msg.UpdateCategoriesUiState -> copy(categoriesUiState = msg.categoriesUiState)

        is Msg.UpdateComments -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(commentsInput = msg.commentsInput)
        )

        is Msg.UpdateCookingTime -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(cookingTimeInput = msg.cookingTimeInput)
        )

        is Msg.UpdateCover -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(cover = msg.cover)
        )

        is Msg.UpdateDescription -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(description = msg.description)
        )

        is Msg.UpdateDishes -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(dishesInput = msg.dishesInput)
        )

        is Msg.UpdateFats -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(fatsInput = msg.fatsInput)
        )

        is Msg.UpdateName -> copy(
            isNameEmptyErrorVisible = msg.name.isBlank(),
            recipeParamsUiState = recipeParamsUiState.copy(name = msg.name)
        )

        is Msg.UpdateNutritions -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(nutritionsInput = msg.nutritionsInput)
        )

        is Msg.UpdatePortions -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(portionsInput = msg.portionsInput)
        )

        is Msg.UpdatePreparationTime -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(preparationTimeInput = msg.preparationTimeInput)
        )

        is Msg.UpdateProteins -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(proteinsInput = msg.proteinsInput)
        )

        is Msg.UpdateRestTime -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(restTimeInput = msg.restTimeInput)
        )

        is Msg.UpdateSelectedCategory -> copy(selectedCategoryIndexInput = msg.index)

        is Msg.UpdateSelectedTag -> copy(selectedTagIndexInput = msg.index)

        is Msg.UpdateSource -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(source = msg.source)
        )

        is Msg.UpdateVideoLink -> copy(
            recipeParamsUiState = recipeParamsUiState
                .copy(videoLink = msg.videoLink)
        )

        is Msg.Ingredient -> reduceIngredientMsg(msg)

        is Msg.Step -> reduceStepMsg(msg)

        is Msg.UpdateUiState -> msg.recipeUiState
            ?.let { copy(uiState = msg.uiState, recipeParamsUiState = it) }
            ?: copy(uiState = msg.uiState)
    }

    private fun State.reduceIngredientMsg(msg: Msg.Ingredient): State = when (msg) {
        is Msg.Ingredient.Add -> copy(
            recipeParamsUiState = recipeParamsUiState.run {
                copy(ingredients = SerializableImmutableList(ingredients with msg.ingredient))
            }
        )

        is Msg.Ingredient.Remove -> copy(
            recipeParamsUiState = recipeParamsUiState.run {
                copy(ingredients = SerializableImmutableList(ingredients without msg.ingredient))
            }
        )

        is Msg.Ingredient.UpdatePortion ->
            copy(ingredientDialogState = ingredientDialogState.copy(portion = msg.portion))

        is Msg.Ingredient.UpdateTitle ->
            copy(ingredientDialogState = ingredientDialogState.copy(title = msg.title))

        is Msg.Ingredient.UpdateDialogVisibility ->
            copy(ingredientDialogState = ingredientDialogState.copy(isVisible = msg.isVisible))
    }

    private fun State.reduceStepMsg(msg: Msg.Step): State = when (msg) {
        is Msg.Step.Add -> copy(
            recipeParamsUiState = recipeParamsUiState.run {
                copy(steps = SerializableImmutableList(steps with msg.step))
            }
        )

        is Msg.Step.Remove -> copy(
            recipeParamsUiState = recipeParamsUiState.run {
                copy(steps = SerializableImmutableList(steps without msg.step))
            }
        )

        is Msg.Step.UpdateDescription ->
            copy(stepDialogState = stepDialogState.copy(description = msg.description))

        is Msg.Step.UpdateCover ->
            copy(stepDialogState = stepDialogState.copy(cover = msg.cover))

        is Msg.Step.UpdateTitle ->
            copy(stepDialogState = stepDialogState.copy(title = msg.title))

        is Msg.Step.UpdateDialogVisibility ->
            copy(stepDialogState = stepDialogState.copy(isVisible = msg.isVisible))
    }
}
