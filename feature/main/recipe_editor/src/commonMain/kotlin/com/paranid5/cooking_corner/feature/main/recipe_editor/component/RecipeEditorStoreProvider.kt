package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.category.CategoryRepository
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.tag.TagRepository
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.entity.TagUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList

internal class RecipeEditorStoreProvider(
    private val storeFactory: StoreFactory,
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val globalEventRepository: GlobalEventRepository,
) {
    sealed interface Msg {
        data class UpdateName(val name: String) : Msg

        data class UpdateDescription(val description: String) : Msg

        data class UpdateSelectedCategory(val index: Int) : Msg

        data class UpdateSelectedTag(val index: Int) : Msg

        data class UpdatePreparationTime(val preparationTimeInput: String) : Msg

        data class UpdateCookingTime(val cookingTimeInput: String) : Msg

        data class UpdateRestTime(val restTimeInput: String) : Msg

        data class UpdatePortions(val portionsInput: String) : Msg

        data class UpdateNutritions(val nutritionsInput: String) : Msg

        data class UpdateProteins(val proteinsInput: String) : Msg

        data class UpdateFats(val fatsInput: String) : Msg

        data class UpdateCarbohydrates(val carbohydratesInput: String) : Msg

        data class UpdateDishes(val dishesInput: String) : Msg

        data class UpdateVideoLink(val videoLink: String) : Msg

        data class UpdateSource(val source: String) : Msg

        data class UpdateCover(val cover: ByteArray?) : Msg

        data class AddIngredient(val ingredient: IngredientUiState) : Msg

        data class RemoveIngredient(val ingredient: IngredientUiState) : Msg

        data class AddStep(val step: StepUiState) : Msg

        data class RemoveStep(val step: StepUiState) : Msg

        data class UpdateCategoriesUiState(
            val categoriesUiState: UiState<SerializableImmutableList<CategoryUiState>>
        ) : Msg

        data class UpdateTagsUiState(
            val tagsUiState: UiState<SerializableImmutableList<TagUiState>>
        ) : Msg
    }

    fun provide(initialState: State): RecipeEditorStore = object :
        RecipeEditorStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "RecipeEditorStore",
            initialState = initialState,
            executorFactory = {
                RecipeExecutor(
                    recipeRepository = recipeRepository,
                    categoryRepository = categoryRepository,
                    tagRepository = tagRepository,
                    globalEventRepository = globalEventRepository,
                )
            },
            reducer = RecipeReducer,
            bootstrapper = SimpleBootstrapper(Unit),
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val recipeRepository: RecipeRepository,
        private val categoryRepository: CategoryRepository,
        private val tagRepository: TagRepository,
        private val globalEventRepository: GlobalEventRepository,
    ) {
        fun create() = RecipeEditorStoreProvider(
            storeFactory = storeFactory,
            recipeRepository = recipeRepository,
            categoryRepository = categoryRepository,
            tagRepository = tagRepository,
            globalEventRepository = globalEventRepository,
        )
    }
}