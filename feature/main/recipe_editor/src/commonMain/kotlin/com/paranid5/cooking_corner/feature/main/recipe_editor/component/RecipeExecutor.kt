package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import arrow.core.Either
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.HttpStatusCode
import com.paranid5.cooking_corner.domain.category.CategoryRepository
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.tag.TagRepository
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStoreProvider.Msg
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.TagUiState
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.mapToImmutableList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class RecipeExecutor(
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val globalEventRepository: GlobalEventRepository,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    @Suppress("LongLine")
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.AddIngredient -> dispatch(Msg.AddIngredient(intent.ingredient))
            is UiIntent.AddStep -> dispatch(Msg.AddStep(intent.step))
            is UiIntent.Back -> publish(Label.Back)
            is UiIntent.RemoveIngredient -> dispatch(Msg.RemoveIngredient(intent.ingredient))
            is UiIntent.RemoveStep -> dispatch(Msg.RemoveStep(intent.step))
            is UiIntent.Save -> scope.launch { onSave() }
            is UiIntent.UpdateCarbohydrates -> dispatch(Msg.UpdateCarbohydrates(intent.carbohydratesInput))
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
            is UiIntent.UpdateSelectedCategory -> dispatch(Msg.UpdateSelectedCategory(intent.index))
            is UiIntent.UpdateSelectedTag -> dispatch(Msg.UpdateSelectedTag(intent.index))
            is UiIntent.UpdateSource -> dispatch(Msg.UpdateSource(intent.source))
            is UiIntent.UpdateThumbnail -> dispatch(Msg.UpdateCover(intent.thumbnail))
            is UiIntent.UpdateVideoLink -> dispatch(Msg.UpdateVideoLink(intent.videoLink))
        }
    }

    override fun executeAction(action: Unit) {
        loadCategories()
        loadTags()
    }

    private fun loadCategories() {
        dispatch(Msg.UpdateCategoriesUiState(UiState.Loading))

        scope.launch {
            handleSpinnerItemsApiResult(
                result = withContext(AppDispatchers.Data) { categoryRepository.getAll() },
                errorMsg = Msg::UpdateCategoriesUiState,
                handleStatus = { handleCategoriesStatus(status = it) }
            )
        }
    }

    private fun loadTags() {
        dispatch(Msg.UpdateTagsUiState(UiState.Loading))

        scope.launch {
            handleSpinnerItemsApiResult(
                result = withContext(AppDispatchers.Data) { tagRepository.getAll() },
                errorMsg = Msg::UpdateTagsUiState,
                handleStatus = { handleTagsStatus(status = it) }
            )
        }
    }

    private suspend fun onSave() {
        // TODO: send request to save recipe
        publish(Label.Back)
    }

    private suspend inline fun handleSpinnerItemsApiResult(
        result: ApiResultWithCode<List<String>>,
        errorMsg: (error: UiState<Nothing>) -> Msg,
        handleStatus: (Either<HttpStatusCode, List<String>>) -> Unit,
    ) = when (result) {
        is Either.Left -> {
            result.value.printStackTrace()
            dispatch(errorMsg(result.value.toUiState()))
        }

        is Either.Right -> handleStatus(result.value)
    }

    private suspend inline fun handleCategoriesStatus(
        status: Either<HttpStatusCode, List<String>>,
    ) = when (status) {
        is Either.Left -> dispatch(Msg.UpdateCategoriesUiState(UiState.Error()))

        is Either.Right -> dispatch(
            Msg.UpdateCategoriesUiState(
                withContext(AppDispatchers.Eval) {
                    status.value
                        .mapToImmutableList(::CategoryUiState)
                        .let(::SerializableImmutableList)
                        .toUiState()
                }
            )
        )
    }

    private suspend inline fun handleTagsStatus(
        status: Either<HttpStatusCode, List<String>>,
    ) = when (status) {
        is Either.Left -> dispatch(Msg.UpdateTagsUiState(UiState.Error()))

        is Either.Right -> dispatch(
            Msg.UpdateTagsUiState(
                withContext(AppDispatchers.Eval) {
                    status.value
                        .mapToImmutableList(::TagUiState)
                        .let(::SerializableImmutableList)
                        .toUiState()
                }
            )
        )
    }
}