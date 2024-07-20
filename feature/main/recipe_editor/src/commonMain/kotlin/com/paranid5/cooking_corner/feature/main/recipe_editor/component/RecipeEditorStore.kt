package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent.Factory.LaunchMode
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State.IngredientDialogState
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State.StepDialogState
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.recipe.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.recipe.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeParamsUiState
import com.paranid5.cooking_corner.ui.entity.recipe.StepUiState
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.orNil
import com.paranid5.cooking_corner.utils.toIntOrZero
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.random.Random

interface RecipeEditorStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data object Back : UiIntent

        data class Load(val recipeId: Long) : UiIntent

        data class Save(
            val launchMode: LaunchMode,
            val unhandledErrorSnackbar: SnackbarMessage,
            val successSnackbar: SnackbarMessage,
        ) : UiIntent

        data class UpdateUiState(val uiState: UiState<Unit>) : UiIntent

        data class UpdateName(val name: String) : UiIntent

        data class UpdateDescription(val description: String) : UiIntent

        data class UpdateSelectedCategory(val index: Int) : UiIntent

        data class UpdatePreparationTime(val preparationTimeInput: String) : UiIntent

        data class UpdateCookingTime(val cookingTimeInput: String) : UiIntent

        data class UpdateRestTime(val restTimeInput: String) : UiIntent

        data class UpdatePortions(val portionsInput: String) : UiIntent

        data class UpdateComments(val commentsInput: String) : UiIntent

        data class UpdateNutritions(val nutritionsInput: String) : UiIntent

        data class UpdateProteins(val proteinsInput: String) : UiIntent

        data class UpdateFats(val fatsInput: String) : UiIntent

        data class UpdateCarbohydrates(val carbohydratesInput: String) : UiIntent

        data class UpdateDishes(val dishesInput: String) : UiIntent

        data class UpdateVideoLink(val videoLink: String) : UiIntent

        data class UpdateSource(val source: String) : UiIntent

        data class UpdateCover(val cover: ByteArray?) : UiIntent

        sealed interface Ingredient : UiIntent {
            data object Add : Ingredient

            data class Remove(val ingredient: IngredientUiState) : Ingredient

            data class UpdateTitle(val title: String) : Ingredient

            data class UpdatePortion(val portion: String) : Ingredient

            data class UpdateDialogState(
                val ingredientDialogState: IngredientDialogState = IngredientDialogState(),
            ) : Ingredient {
                constructor(isVisible: Boolean) : this(
                    ingredientDialogState = IngredientDialogState(isVisible = isVisible),
                )
            }
        }

        sealed interface Step : UiIntent {
            data object Add : Step

            data class Remove(val step: StepUiState) : Step

            data class UpdateTitle(val title: String) : Step

            data class UpdateDescription(val description: String) : Step

            data class UpdateCover(val cover: ByteArray?) : Step

            data class UpdateDialogState(
                val stepDialogState: StepDialogState = StepDialogState(),
            ) : Step {
                constructor(isVisible: Boolean) : this(
                    stepDialogState = StepDialogState(isVisible = isVisible)
                )
            }
        }
    }

    @Serializable
    @Immutable
    data class State(
        val launchMode: LaunchMode,
        val recipeParamsUiState: RecipeParamsUiState = RecipeParamsUiState(),
        val categoriesUiState: UiState<SerializableImmutableList<CategoryUiState>> = UiState.Undefined,
        val ingredientDialogState: IngredientDialogState = IngredientDialogState(),
        val stepDialogState: StepDialogState = StepDialogState(),
        val isAddStepDialogVisible: Boolean = false,
        val selectedCategoryIndexInput: Int = NOT_SELECTED,
        val selectedTagIndexInput: Int = NOT_SELECTED,
        val isNameEmptyErrorVisible: Boolean = false,
        val isCategoryEmptyErrorVisible: Boolean = false,
        val uiState: UiState<Unit> = UiState.Undefined,
    ) {
        companion object {
            const val NOT_SELECTED = 0
            private const val SPINNER_ITEM_OFFSET = 1
        }

        @Serializable
        @Immutable
        data class IngredientDialogState(
            val isVisible: Boolean = false,
            val title: String = "",
            val portion: String = "",
            val key: Long = Random.nextLong(),
        ) {
            @Transient
            val inputIngredientUiState = IngredientUiState(
                title = title,
                portion = portion,
                key = key,
            )

            companion object {
                fun fromUiState(ingredientUiState: IngredientUiState) =
                    IngredientDialogState(
                        isVisible = true,
                        title = ingredientUiState.title,
                        portion = ingredientUiState.portion,
                        key = ingredientUiState.key,
                    )
            }
        }

        @Serializable
        @Immutable
        data class StepDialogState(
            val isVisible: Boolean = false,
            val title: String = "",
            val description: String = "",
            val cover: ImageContainer? = null,
            val key: Long = Random.nextLong(),
        ) {
            @Transient
            val inputStepUiState = StepUiState(
                title = title,
                description = description,
                cover = cover,
                key = key,
            )

            companion object {
                fun fromUiState(stepUiState: StepUiState) =
                    StepDialogState(
                        isVisible = true,
                        title = stepUiState.title,
                        description = stepUiState.description,
                        cover = stepUiState.cover,
                        key = stepUiState.key,
                    )
            }
        }

        @Transient
        val isNameEmpty = recipeParamsUiState.name.isEmpty()

        @Transient
        val categories = categoriesUiState.getOrNull().orNil()

        @Transient
        val selectedCategoryIndex = when (selectedCategoryIndexInput) {
            NOT_SELECTED -> categories
                .indexOfFirst { it.title == recipeParamsUiState.initialCategory }
                .takeIf { it > -1 }
                ?.let { it + SPINNER_ITEM_OFFSET }
                ?: NOT_SELECTED

            else -> selectedCategoryIndexInput
        }

        @Transient
        val selectedCategoryTitleOrNull = selectedCategoryIndex
            .takeIf { it > NOT_SELECTED }
            ?.let { index ->
                categoriesUiState
                    .getOrNull()
                    ?.getOrNull(index - SPINNER_ITEM_OFFSET)
                    ?.title
            }

        @Transient
        val isCategorySelected = selectedCategoryTitleOrNull != null

        @Transient
        val preparationTimeMinutes = recipeParamsUiState.preparationTimeInput.toIntOrZero()

        @Transient
        val cookingTimeMinutes = recipeParamsUiState.cookingTimeInput.toIntOrZero()

        @Transient
        val restTimeMinutes = recipeParamsUiState.restTimeInput.toIntOrZero()

        @Transient
        val totalTimeMinutes = preparationTimeMinutes + cookingTimeMinutes + restTimeMinutes
    }

    sealed interface Label {
        data object Back : Label
    }
}
