package com.paranid5.cooking_corner.domain.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRecipeRequest(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("icon_path") val iconPath: String? = null,
    @SerialName("category_name") val category: String? = null,
    @SerialName("tag_name") val tag: String? = null,
    @SerialName("preparing_time") val preparingTime: Int? = null,
    @SerialName("cooking_time") val cookingTime: Int? = null,
    @SerialName("waiting_time") val waitingTime: Int? = null,
    @SerialName("total_time") val totalTime: Int? = null,
    @SerialName("ingredients") val ingredients: List<IngredientDTO> = emptyList(),
    @SerialName("steps") val steps: List<StepDTO> = emptyList(),
    @SerialName("portions") val portions: Int? = null,
    @SerialName("comments") val comments: String? = null,
    @SerialName("nutritional_value") val nutritions: Int? = null,
    @SerialName("proteins_value") val proteins: Int? = null,
    @SerialName("fats_value") val fats: Int? = null,
    @SerialName("carbohydrates_value") val carbohydrates: Int? = null,
    @SerialName("dishes") val dishes: Int? = null,
    @SerialName("video_link") val videoLink: String? = null,
    @SerialName("source") val source: String? = null,
)