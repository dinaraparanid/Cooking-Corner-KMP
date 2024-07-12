package com.paranid5.cooking_corner.domain.tag.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagRequest(
    @SerialName("name") val name: String,
)
