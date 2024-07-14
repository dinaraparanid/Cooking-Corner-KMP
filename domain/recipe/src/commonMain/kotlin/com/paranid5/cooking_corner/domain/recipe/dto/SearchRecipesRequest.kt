package com.paranid5.cooking_corner.domain.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRecipesRequest(
    @SerialName("category_name") val categoryName: String = "",
    @SerialName("is_favourite") val isFavourite: Boolean = false,
    @SerialName("ascending_order") val ascendingOrder: Boolean = true,
)