package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent.Factory.LaunchMode
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeParamsUiState
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.entity.TagUiState
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.orNil
import com.paranid5.cooking_corner.utils.toIntOrZero
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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

        data class UpdateSelectedTag(val index: Int) : UiIntent

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

        data class UpdateThumbnail(val thumbnail: ByteArray?) : UiIntent

        sealed interface Ingredient : UiIntent {
            data object Add : Ingredient
            data class Remove(val ingredient: IngredientUiState) : Ingredient
            data class UpdateTitle(val title: String) : Ingredient
            data class UpdatePortion(val portion: String) : Ingredient
            data class UpdateDialogVisibility(val isVisible: Boolean) : Ingredient
        }

        sealed interface Step : UiIntent {
            data object Add : Step
            data class Remove(val step: StepUiState) : Step
            data class UpdateTitle(val title: String) : Step
            data class UpdateDescription(val description: String) : Step
            data class UpdateCover(val cover: ByteArray?) : Step
            data class UpdateDialogVisibility(val isVisible: Boolean) : Step
        }
    }

    @Serializable
    @Immutable
    data class State(
        val launchMode: LaunchMode,
        val recipeParamsUiState: RecipeParamsUiState = RecipeParamsUiState(),
        val categoriesUiState: UiState<SerializableImmutableList<CategoryUiState>> = UiState.Undefined,
        val tagsUiState: UiState<SerializableImmutableList<TagUiState>> = UiState.Undefined,
        val ingredientDialogState: IngredientDialogState = IngredientDialogState(),
        val stepDialogState: StepDialogState = StepDialogState(),
        val isAddStepDialogVisible: Boolean = false,
        val isNameEmptyErrorVisible: Boolean = false,
        val selectedCategoryIndexInput: Int = NOT_SELECTED,
        val selectedTagIndexInput: Int = NOT_SELECTED,
        val uiState: UiState<Unit> = UiState.Undefined,
    ) {
        companion object {
            const val NOT_SELECTED = 0
            private const val SPINNER_ITEM_OFFSET = 1
        }

        @Serializable
        @Immutable
        data class IngredientDialogState(
            val isVisible: Boolean,
            val title: String,
            val portion: String,
        ) {
            @Transient
            val inputIngredientUiState = IngredientUiState(
                title = title,
                portion = portion,
            )

            constructor() : this(isVisible = false, title = "", portion = "")
        }

        @Serializable
        @Immutable
        data class StepDialogState(
            val isVisible: Boolean,
            val title: String,
            val description: String,
            val cover: ByteArray?,
        ) {
            @Transient
            val inputStepUiState = StepUiState(
                title = title,
                description = description,
                coverUrlState = UiState.Success, // TODO: handle cover
            )

            constructor() : this(
                isVisible = false,
                title = "",
                description = "",
                cover = null,
            )
        }

        @Transient
        val isNameEmpty = recipeParamsUiState.name.isEmpty()

        @Transient
        val categories = categoriesUiState.getOrNull().orNil()

        @Transient
        val tags = tagsUiState.getOrNull().orNil()

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
        val selectedCategoryTitle = selectedCategoryTitleOrNull.orEmpty()

        @Transient
        val selectedTagIndex = when (selectedTagIndexInput) {
            NOT_SELECTED -> tags
                .indexOfFirst { it.title == recipeParamsUiState.initialTag }
                .takeIf { it > -1 }
                ?.let { it + SPINNER_ITEM_OFFSET }
                ?: NOT_SELECTED

            else -> selectedTagIndexInput
        }

        @Transient
        val selectedTagTitleOrNull = selectedTagIndex
            .takeIf { it > NOT_SELECTED }
            ?.let { index ->
                tagsUiState
                    .getOrNull()
                    ?.getOrNull(index - SPINNER_ITEM_OFFSET)
                    ?.title
            }

        @Transient
        val selectedTagTitle = selectedTagTitleOrNull.orEmpty()

        @Transient
        val isSaveButtonEnabled = isNameEmpty.not() && isCategorySelected

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
