package com.paranid5.cooking_corner.feature.main.recipe_editor.domain

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.entity.mappers.fromResponse
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.mapToImmutableList
import com.paranid5.cooking_corner.utils.orNil
import com.paranid5.cooking_corner.utils.toStringOrEmpty
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
    val cover: ByteArray? = null,
    val ingredients: SerializableImmutableList<IngredientUiState> = SerializableImmutableList(),
    val steps: SerializableImmutableList<StepUiState> = SerializableImmutableList(),
) {
    companion object {
        fun fromResponse(response: RecipeResponse) = RecipeParamsUiState(
            id = response.id,
            name = response.name,
            description = response.description.orEmpty(),
            initialCategory = response.category.orEmpty(),
            initialTag = response.tag.orEmpty(),
            preparationTimeInput = response.preparingTime.toStringOrEmpty(),
            cookingTimeInput = response.cookingTime.toStringOrEmpty(),
            restTimeInput = response.waitingTime.toStringOrEmpty(),
            portionsInput = response.portions.toStringOrEmpty(),
            commentsInput = response.comments.orEmpty(),
            nutritionsInput = response.nutritions.toStringOrEmpty(),
            proteinsInput = response.proteins.toStringOrEmpty(),
            fatsInput = response.fats.toStringOrEmpty(),
            carbohydratesInput = response.carbohydrates.toStringOrEmpty(),
            dishesInput = response.dishes.orEmpty(),
            videoLink = response.videoLink.orEmpty(),
            source = response.source.orEmpty(),
            ingredients = SerializableImmutableList(
                response.ingredients
                    ?.mapToImmutableList(IngredientUiState.Companion::fromResponse)
                    .orNil()
            ),
            steps = SerializableImmutableList(
                response.steps
                    ?.mapToImmutableList(StepUiState.Companion::fromResponse)
                    .orNil()
            ),
        )
    }
}