package com.paranid5.cooking_corner.domain.recipe.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("name") val name: String,
    @SerialName("rating") val rating: Float,
    @SerialName("creator_username") val username: String,
    @SerialName("is_my_recipe") val isMyRecipe: Boolean,
    @SerialName("is_private") val isPrivate: Boolean,
    @SerialName("preparing_time") val preparingTime: Int? = null,
    @SerialName("cooking_time") val cookingTime: Int? = null,
    @SerialName("is_favourite") val isFavourite: Boolean = false,
    @SerialName("reviews") val reviews: Int? = null,
    @SerialName("portions") val portions: Int? = null,
    @SerialName("icon_path") val iconPath: String? = null,
)