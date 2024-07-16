package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.category.CategoryRepository
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State.IngredientDialogState
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State.StepDialogState
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.RecipeParamsUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList

internal class RecipeEditorStoreProvider(
    private val storeFactory: StoreFactory,
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository,
    private val globalEventRepository: GlobalEventRepository,
) {
    sealed interface Msg {
        data class UpdateUiState(
            val uiState: UiState<Unit>,
            val recipeUiState: RecipeParamsUiState? = null,
        ) : Msg

        data class UpdateName(val name: String) : Msg

        data class UpdateDescription(val description: String) : Msg

        data class UpdateSelectedCategory(val index: Int) : Msg

        data class UpdateSelectedTag(val index: Int) : Msg

        data class UpdatePreparationTime(val preparationTimeInput: String) : Msg

        data class UpdateCookingTime(val cookingTimeInput: String) : Msg

        data class UpdateRestTime(val restTimeInput: String) : Msg

        data class UpdatePortions(val portionsInput: String) : Msg

        data class UpdateComments(val commentsInput: String) : Msg

        data class UpdateNutritions(val nutritionsInput: String) : Msg

        data class UpdateProteins(val proteinsInput: String) : Msg

        data class UpdateFats(val fatsInput: String) : Msg

        data class UpdateCarbohydrates(val carbohydratesInput: String) : Msg

        data class UpdateDishes(val dishesInput: String) : Msg

        data class UpdateVideoLink(val videoLink: String) : Msg

        data class UpdateSource(val source: String) : Msg

        data class UpdateCover(val cover: ImageContainer) : Msg

        data class UpdateCategoriesUiState(
            val categoriesUiState: UiState<SerializableImmutableList<CategoryUiState>>
        ) : Msg

        sealed interface Ingredient : Msg {
            data class Add(val ingredient: IngredientUiState) : Ingredient

            data class Remove(val ingredient: IngredientUiState) : Ingredient

            data class UpdateTitle(val title: String) : Ingredient

            data class UpdatePortion(val portion: String) : Ingredient

            data class UpdateDialogState(
                val dialogState: IngredientDialogState = IngredientDialogState(),
            ) : Ingredient
        }

        sealed interface Step : Msg {
            data class Add(val step: StepUiState) : Step

            data class Remove(val step: StepUiState) : Step

            data class UpdateTitle(val title: String) : Step

            data class UpdateDescription(val description: String) : Step

            data class UpdateCover(val cover: ByteArray?) : Step

            data class UpdateDialogState(
                val dialogState: StepDialogState = StepDialogState(),
            ) : Step
        }
    }

    fun provide(initialState: State): RecipeEditorStore = object :
        RecipeEditorStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "RecipeEditorStore",
            initialState = initialState,
            executorFactory = {
                RecipeEditorExecutor(
                    recipeRepository = recipeRepository,
                    categoryRepository = categoryRepository,
                    globalEventRepository = globalEventRepository,
                )
            },
            reducer = RecipeEditorReducer,
            bootstrapper = SimpleBootstrapper(Unit),
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val recipeRepository: RecipeRepository,
        private val categoryRepository: CategoryRepository,
        private val globalEventRepository: GlobalEventRepository,
    ) {
        fun create() = RecipeEditorStoreProvider(
            storeFactory = storeFactory,
            recipeRepository = recipeRepository,
            categoryRepository = categoryRepository,
            globalEventRepository = globalEventRepository,
        )
    }
}
