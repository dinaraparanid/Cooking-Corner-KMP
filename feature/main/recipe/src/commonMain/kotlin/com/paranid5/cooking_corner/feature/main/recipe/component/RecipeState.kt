package com.paranid5.cooking_corner.feature.main.recipe.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.utils.serializer.ImmutableListSerializer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RecipeState(
    val recipe: RecipeDetailedUiState,
    @Serializable(with = ImmutableListSerializer::class)
    val steps: ImmutableList<StepUiState>,
    @Serializable(with = ImmutableListSerializer::class)
    val ingredients: ImmutableList<IngredientUiState>,
    val isKebabMenuVisible: Boolean,
) {
    constructor(
        recipe: RecipeDetailedUiState,
        steps: ImmutableList<StepUiState>,
        ingredients: ImmutableList<IngredientUiState>,
    ) : this(
        recipe = recipe,
        steps = steps,
        ingredients = ingredients,
        isKebabMenuVisible = false,
    )
}