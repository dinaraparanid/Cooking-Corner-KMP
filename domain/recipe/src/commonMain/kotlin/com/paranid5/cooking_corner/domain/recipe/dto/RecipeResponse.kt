package com.paranid5.cooking_corner.domain.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("icon_path") val iconPath: String? = null,
    @SerialName("rating") val rating: Float? = null,
    @SerialName("reviews") val reviews: Int? = null,
    @SerialName("preparing_time") val preparingTime: Int? = null,
    @SerialName("cooking_time") val cookingTime: Int? = null,
    @SerialName("waiting_time") val waitingTime: Int? = null,
    @SerialName("total_time") val totalTime: Int? = null,
    @SerialName("ingredients") val ingredients: List<IngredientDTO>? = null,
    @SerialName("steps") val steps: List<StepDTO>? = null,
    @SerialName("portions") val portions: Int? = null,
    @SerialName("comments") val comments: String? = null,
    @SerialName("nutritional_value") val nutritions: Float? = null,
    @SerialName("proteins_value") val proteins: Float? = null,
    @SerialName("fats_value") val fats: Float? = null,
    @SerialName("carbohydrates_value") val carbohydrates: Float? = null,
    @SerialName("dishes") val dishes: String? = null,
    @SerialName("video_link") val videoLink: String? = null,
    @SerialName("source") val source: String? = null,
    @SerialName("is_private") val isPrivate: Boolean? = null,
    @SerialName("is_my_recipe") val isMyRecipe: Boolean? = null,
    @SerialName("is_favorite") val isFavourite: Boolean? = null,
    @SerialName("creator_username") val username: String? = null,
    @SerialName("category_name") val category: String? = null,
    @SerialName("tag_name") val tag: String? = null,
    @SerialName("my_rating") val myRating: Float? = null,
)
