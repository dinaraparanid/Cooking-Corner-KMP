package com.paranid5.cooking_corner.ui.entity.mappers

import com.paranid5.cooking_corner.domain.recipe.dto.IngredientDTO
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.recipe.dto.StepDTO
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.mapToImmutableList
import com.paranid5.cooking_corner.utils.orNil

fun RecipeUiState.Companion.fromResponse(response: RecipeResponse) =
    RecipeUiState(
        id = response.id,
        title = response.name,
        rating = response.rating ?: 0F,
        preparingTime = response.preparingTime ?: 0,
        cookingTime = response.cookingTime ?: 0,
        author = response.username,
        isLiked = response.isFavourite,
        isMyRecipe = response.isMyRecipe,
        coverUrlState = response.iconPath?.toUiState() ?: UiState.Success,
    )

fun RecipeDetailedUiState.Companion.fromResponse(response: RecipeResponse) =
    RecipeDetailedUiState(
        id = response.id,
        title = response.name,
        description = response.description.orEmpty(),
        rating = response.rating ?: 0F,
        myRating = response.myRating,
        preparingTime = response.preparingTime ?: 0,
        cookingTime = response.cookingTime ?: 0,
        author = response.username,
        isLiked = response.isFavourite,
        reviews = response.reviews ?: 0,
        portions = response.portions ?: 0,
        byUser = response.isMyRecipe,
        isPublished = response.isPrivate.not(),
        coverUrlState = response.iconPath?.toUiState() ?: UiState.Success,
        ingredients = SerializableImmutableList(
            response
                .ingredients
                ?.mapToImmutableList(IngredientUiState.Companion::fromResponse)
                .orNil()
        ),
        steps = SerializableImmutableList(
            response
                .steps
                ?.mapToImmutableList(StepUiState.Companion::fromResponse)
                .orNil()
        ),
    )

fun IngredientUiState.Companion.fromResponse(response: IngredientDTO) =
    IngredientUiState(
        title = response.title.orEmpty(),
        portion = response.portion.orEmpty(),
    )

fun IngredientUiState.toRequest() =
    IngredientDTO(title = title, portion = portion)

fun StepUiState.Companion.fromResponse(response: StepDTO) =
    StepUiState(
        title = response.title.orEmpty(),
        description = response.description.orEmpty(),
        coverUrlState = response.imagePath?.toUiState() ?: UiState.Success,
    )

fun StepUiState.toRequest() =
    StepDTO(title = title, description = description, imagePath = coverUrlState.getOrNull())
