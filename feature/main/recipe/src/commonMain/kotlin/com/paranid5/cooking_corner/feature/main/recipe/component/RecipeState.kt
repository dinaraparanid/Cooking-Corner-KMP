package com.paranid5.cooking_corner.feature.main.recipe.component

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.feature.main.recipe.entity.IngredientUiState
import com.paranid5.cooking_corner.feature.main.recipe.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.feature.main.recipe.entity.StepUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class RecipeState(
    val recipe: RecipeDetailedUiState,
    val steps: ImmutableList<StepUiState>,
    val ingredients: ImmutableList<IngredientUiState>,
)