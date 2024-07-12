package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.entity.TagUiState
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.orNil
import com.paranid5.cooking_corner.utils.serializer.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

interface RecipeEditorStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data object Back : UiIntent
        data object Save : UiIntent
        data class UpdateName(val name: String) : UiIntent
        data class UpdateDescription(val description: String) : UiIntent
        data class UpdateSelectedCategory(val index: Int) : UiIntent
        data class UpdateSelectedTag(val index: Int) : UiIntent
        data class UpdatePreparationTime(val preparationTimeInput: String) : UiIntent
        data class UpdateCookingTime(val cookingTimeInput: String) : UiIntent
        data class UpdateRestTime(val restTimeInput: String) : UiIntent
        data class UpdatePortions(val portionsInput: String) : UiIntent
        data class UpdateNutritions(val nutritionsInput: String) : UiIntent
        data class UpdateProteins(val proteinsInput: String) : UiIntent
        data class UpdateFats(val fatsInput: String) : UiIntent
        data class UpdateCarbohydrates(val carbohydratesInput: String) : UiIntent
        data class UpdateDishes(val dishesInput: String) : UiIntent
        data class UpdateVideoLink(val videoLink: String) : UiIntent
        data class UpdateSource(val source: String) : UiIntent
        data class UpdateThumbnail(val thumbnail: ByteArray?) : UiIntent
        data class AddIngredient(val ingredient: IngredientUiState) : UiIntent
        data class RemoveIngredient(val ingredient: IngredientUiState) : UiIntent
        data class AddStep(val step: StepUiState) : UiIntent
        data class RemoveStep(val step: StepUiState) : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val name: String = "",
        val description: String = "",
        val selectedCategoryIndex: Int = NOT_SELECTED,
        val selectedTagIndex: Int = NOT_SELECTED,
        val preparationTimeInput: String = "",
        val cookingTimeInput: String = "",
        val restTimeInput: String = "",
        val portionsInput: String = "",
        val nutritionsInput: String = "",
        val proteinsInput: String = "",
        val fatsInput: String = "",
        val carbohydratesInput: String = "",
        val dishesInput: String = "",
        val videoLink: String = "",
        val source: String = "",
        val cover: ByteArray? = null,
        @Serializable(with = ImmutableListSerializer::class)
        val ingredients: ImmutableList<IngredientUiState> = persistentListOf(),
        @Serializable(with = ImmutableListSerializer::class)
        val steps: ImmutableList<StepUiState> = persistentListOf(),
        val categoriesUiState: UiState<SerializableImmutableList<CategoryUiState>> = UiState.Undefined,
        val tagsUiState: UiState<SerializableImmutableList<TagUiState>> = UiState.Undefined,
    ) {
        companion object {
            internal const val NOT_SELECTED = 0
        }

        @Transient
        val selectedCategoryTitle = selectedCategoryIndex
            .takeIf { it > NOT_SELECTED }
            ?.let { index ->
                categoriesUiState
                    .getOrNull()
                    ?.value
                    ?.getOrNull(index)
                    ?.title
            }
            .orEmpty()

        @Transient
        val selectedTagTitle = selectedTagIndex
            .takeIf { it > NOT_SELECTED }
            ?.let { index ->
                tagsUiState
                    .getOrNull()
                    ?.value
                    ?.getOrNull(index)
                    ?.title
            }
            .orEmpty()

        @Transient
        val categories = categoriesUiState
            .getOrNull()
            ?.value
            ?.toImmutableList()
            .orNil()

        @Transient
        val tags = tagsUiState
            .getOrNull()
            ?.value
            ?.toImmutableList()
            .orNil()

        @Transient
        val preparationTimeMinutes = preparationTimeInput.toIntOrNull() ?: 0

        @Transient
        val cookingTimeMinutes = cookingTimeInput.toIntOrNull() ?: 0

        @Transient
        val restTimeMinutes = restTimeInput.toIntOrNull() ?: 0

        @Transient
        val totalTimeMinutes = preparationTimeMinutes + cookingTimeMinutes + restTimeMinutes
    }

    sealed interface Label {
        data object Back : Label
    }
}