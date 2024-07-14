package com.paranid5.cooking_corner.ui.entity.mappers

import com.paranid5.cooking_corner.domain.recipe.dto.IngredientDTO
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeResponse
import com.paranid5.cooking_corner.domain.recipe.dto.StepDTO
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.entity.RecipeParamsUiState
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.mapToImmutableList
import com.paranid5.cooking_corner.utils.orNil
import com.paranid5.cooking_corner.utils.toStringOrEmpty

fun RecipeUiState.Companion.fromResponse(response: RecipeResponse) =
    RecipeUiState(
        id = response.id ?: 0,
        title = response.name.orEmpty(),
        rating = response.rating ?: 0F,
        preparingTime = response.preparingTime ?: 0,
        cookingTime = response.cookingTime ?: 0,
        author = response.username.orEmpty(),
        isLiked = response.isFavourite ?: false,
        isMyRecipe = response.isMyRecipe ?: false,
        coverUrlState = response.iconPath?.toUiState() ?: UiState.Success,
    )

fun RecipeDetailedUiState.Companion.fromResponse(response: RecipeResponse) =
    RecipeDetailedUiState(
        id = response.id ?: 0,
        title = response.name.orEmpty(),
        description = response.description.orEmpty(),
        rating = response.rating ?: 0F,
        myRating = response.myRating,
        preparingTime = response.preparingTime ?: 0,
        cookingTime = response.cookingTime ?: 0,
        author = response.username.orEmpty(),
        isLiked = response.isFavourite ?: false,
        reviews = response.reviews ?: 0,
        portions = response.portions ?: 0,
        byUser = response.isMyRecipe ?: false,
        isPublished = response.isPrivate?.not() ?: false,
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

fun RecipeParamsUiState.Companion.fromResponse(response: RecipeResponse) =
    RecipeParamsUiState(
        id = response.id,
        name = response.name.orEmpty(),
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
