package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStoreProvider.Msg
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.with
import com.paranid5.cooking_corner.utils.without

internal object RecipeReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg): State = when (msg) {
        is Msg.UpdateCarbohydrates -> copy(carbohydratesInput = msg.carbohydratesInput)
        is Msg.UpdateCategoriesUiState -> copy(categoriesUiState = msg.categoriesUiState)
        is Msg.UpdateComments -> copy(commentsInput = msg.commentsInput)
        is Msg.UpdateCookingTime -> copy(cookingTimeInput = msg.cookingTimeInput)
        is Msg.UpdateCover -> copy(cover = msg.cover)
        is Msg.UpdateDescription -> copy(description = msg.description)
        is Msg.UpdateDishes -> copy(dishesInput = msg.dishesInput)
        is Msg.UpdateFats -> copy(fatsInput = msg.fatsInput)
        is Msg.UpdateName -> copy(name = msg.name)
        is Msg.UpdateNutritions -> copy(nutritionsInput = msg.nutritionsInput)
        is Msg.UpdatePortions -> copy(portionsInput = msg.portionsInput)
        is Msg.UpdatePreparationTime -> copy(preparationTimeInput = msg.preparationTimeInput)
        is Msg.UpdateProteins -> copy(proteinsInput = msg.proteinsInput)
        is Msg.UpdateRestTime -> copy(restTimeInput = msg.restTimeInput)
        is Msg.UpdateSelectedCategory -> copy(selectedCategoryIndex = msg.index)
        is Msg.UpdateSelectedTag -> copy(selectedTagIndex = msg.index)
        is Msg.UpdateSource -> copy(source = msg.source)
        is Msg.UpdateTagsUiState -> copy(tagsUiState = msg.tagsUiState)
        is Msg.UpdateVideoLink -> copy(videoLink = msg.videoLink)
        is Msg.Ingredient -> reduceIngredientMsg(msg)
        is Msg.Step -> reduceStepMsg(msg)
    }

    private fun State.reduceIngredientMsg(msg: Msg.Ingredient): State = when (msg) {
        is Msg.Ingredient.Add ->
            copy(ingredients = SerializableImmutableList(ingredients with msg.ingredient))

        is Msg.Ingredient.Remove ->
            copy(ingredients = SerializableImmutableList(ingredients without msg.ingredient))

        is Msg.Ingredient.UpdatePortion ->
            copy(ingredientDialogState = ingredientDialogState.copy(portion = msg.portion))

        is Msg.Ingredient.UpdateTitle ->
            copy(ingredientDialogState = ingredientDialogState.copy(title = msg.title))

        is Msg.Ingredient.UpdateDialogVisibility ->
            copy(ingredientDialogState = ingredientDialogState.copy(isVisible = msg.isVisible))
    }

    private fun State.reduceStepMsg(msg: Msg.Step): State = when (msg) {
        is Msg.Step.Add ->
            copy(steps = SerializableImmutableList(steps with msg.step))

        is Msg.Step.Remove ->
            copy(steps = SerializableImmutableList(steps without msg.step))

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
