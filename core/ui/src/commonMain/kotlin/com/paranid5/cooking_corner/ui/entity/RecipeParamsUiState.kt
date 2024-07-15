package com.paranid5.cooking_corner.ui.entity

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RecipeParamsUiState(
    val id: Long? = null,
    val name: String = "",
    val description: String = "",
    val initialCategory: String? = null,
    val initialTag: String? = null,
    val preparationTimeInput: String = "",
    val cookingTimeInput: String = "",
    val restTimeInput: String = "",
    val portionsInput: String = "",
    val commentsInput: String = "",
    val nutritionsInput: String = "",
    val proteinsInput: String = "",
    val fatsInput: String = "",
    val carbohydratesInput: String = "",
    val dishesInput: String = "",
    val videoLink: String = "",
    val source: String = "",
    val cover: ImageContainer? = null,
    val ingredients: SerializableImmutableList<IngredientUiState> = SerializableImmutableList(),
    val steps: SerializableImmutableList<StepUiState> = SerializableImmutableList(),
)
