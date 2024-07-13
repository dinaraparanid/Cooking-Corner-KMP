package com.paranid5.cooking_corner.domain.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipeModifyParams(
    val name: String,
    val description: String?,
    val iconPath: String?,
    val category: String?,
    val tag: String?,
    val preparingTime: Int?,
    val cookingTime: Int?,
    val waitingTime: Int?,
    val totalTime: Int?,
    val ingredients: List<IngredientDTO>,
    val steps: List<StepDTO>,
    val portions: Int?,
    val comments: String?,
    val nutritions: Int?,
    val proteins: Int?,
    val fats: Int?,
    val carbohydrates: Int?,
    val dishes: String?,
    val videoLink: String?,
    val source: String?,
)
