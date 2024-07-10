package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStoreProvider.Msg
import kotlinx.coroutines.launch

internal class RecipeExecutor(
    private val recipeRepository: RecipeRepository,
    private val globalEventRepository: GlobalEventRepository,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    @Suppress("LongLine")
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.AddIngredient -> dispatch(Msg.AddIngredient(intent.ingredient))
            is UiIntent.AddStep -> dispatch(Msg.AddStep(intent.step))
            is UiIntent.Back -> publish(Label.Back)
            is UiIntent.PickThubnail -> publish(Label.PickCover)
            is UiIntent.RemoveIngredient -> dispatch(Msg.RemoveIngredient(intent.ingredient))
            is UiIntent.RemoveStep -> dispatch(Msg.RemoveStep(intent.step))
            is UiIntent.Save -> scope.launch { onSave() }
            is UiIntent.UpdateCarbohydrates -> dispatch(Msg.UpdateCarbohydrates(intent.carbohydratesInput))
            is UiIntent.UpdateCategory -> dispatch(Msg.UpdateCategory(intent.category))
            is UiIntent.UpdateCookingTime -> dispatch(Msg.UpdateCookingTime(intent.cookingTimeInput))
            is UiIntent.UpdateDescription -> dispatch(Msg.UpdateDescription(intent.description))
            is UiIntent.UpdateDishes -> dispatch(Msg.UpdateDishes(intent.dishesInput))
            is UiIntent.UpdateFats -> dispatch(Msg.UpdateFats(intent.fatsInput))
            is UiIntent.UpdateName -> dispatch(Msg.UpdateName(intent.name))
            is UiIntent.UpdateNutritions -> dispatch(Msg.UpdateNutritions(intent.nutritionsInput))
            is UiIntent.UpdatePortions -> dispatch(Msg.UpdatePortions(intent.portionsInput))
            is UiIntent.UpdatePreparationTime -> dispatch(Msg.UpdatePreparationTime(intent.preparationTimeInput))
            is UiIntent.UpdateProteins -> dispatch(Msg.UpdateProteins(intent.proteinsInput))
            is UiIntent.UpdateRestTime -> dispatch(Msg.UpdateRestTime(intent.restTimeInput))
            is UiIntent.UpdateSource -> dispatch(Msg.UpdateSource(intent.source))
            is UiIntent.UpdateTag -> dispatch(Msg.UpdateTag(intent.tag))
            is UiIntent.UpdateThumbnail -> dispatch(Msg.UpdateCover(intent.thumbnail))
            is UiIntent.UpdateVideoLink -> dispatch(Msg.UpdateVideoLink(intent.videoLink))
        }
    }

    override fun executeAction(action: Unit) {
        loadCategories()
        loadTags()
    }

    private fun loadCategories() {
        // dispatch(Msg.UpdateCategoriesUiState(UiState.Loading))
        // TODO: load categories
    }

    private fun loadTags() {
        // dispatch(Msg.UpdateTagsUiState(UiState.Loading))
        // TODO: load tags
    }

    private suspend fun onSave() {
        // TODO: send request to save recipe
        publish(Label.Back)
    }
}