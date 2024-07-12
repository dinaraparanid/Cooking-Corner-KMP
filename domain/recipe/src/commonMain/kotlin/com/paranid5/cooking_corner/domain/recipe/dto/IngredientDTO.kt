package com.paranid5.cooking_corner.domain.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientDTO(
    @SerialName("title") val title: String? = null,
    @SerialName("portion") val portion: String? = null,
)