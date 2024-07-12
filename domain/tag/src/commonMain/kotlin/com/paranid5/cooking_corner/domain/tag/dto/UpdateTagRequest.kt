package com.paranid5.cooking_corner.domain.tag.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTagRequest(
    @SerialName("old_name") val oldName: String,
    @SerialName("new_name") val newName: String,
)
