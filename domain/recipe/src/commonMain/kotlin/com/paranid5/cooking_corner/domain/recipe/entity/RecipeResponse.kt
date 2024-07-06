package com.paranid5.cooking_corner.domain.recipe.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("name") val name: String,
    @SerialName("rating") val rating: Float,
    @SerialName("preparing_time") val preparingTime: Int,
    @SerialName("cooking_time") val cookingTime: Int,
    @SerialName("creator_username") val username: String,
    @SerialName("is_favourite") val isFavourite: Boolean,
    @SerialName("reviews") val reviews: Int,
    @SerialName("dishes") val dishes: Int,
    @SerialName("is_my_recipe") val isMyRecipe: Boolean,
    @SerialName("is_private") val isPrivate: Boolean,
    @SerialName("icon_path") val iconPath: String?,
)