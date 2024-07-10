package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStoreProvider.Msg
import com.paranid5.cooking_corner.utils.with
import com.paranid5.cooking_corner.utils.without

internal object RecipeReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg): State = when (msg) {
        is Msg.AddIngredient -> copy(ingredients = ingredients with msg.ingredient)
        is Msg.AddStep -> copy(steps = steps with msg.step)
        is Msg.RemoveIngredient -> copy(ingredients = ingredients without msg.ingredient)
        is Msg.RemoveStep -> copy(steps = steps without msg.step)
        is Msg.UpdateCarbohydrates -> copy(carbohydratesInput = msg.carbohydratesInput)
        is Msg.UpdateCategoriesUiState -> copy(categoriesUiState = msg.categoriesUiState)
        is Msg.UpdateCategory -> copy(category = msg.category)
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
        is Msg.UpdateSource -> copy(source = msg.source)
        is Msg.UpdateTag -> copy(tag = msg.tag)
        is Msg.UpdateTagsUiState -> copy(tagsUiState = msg.tagsUiState)
        is Msg.UpdateVideoLink -> copy(videoLink = msg.videoLink)
    }
}