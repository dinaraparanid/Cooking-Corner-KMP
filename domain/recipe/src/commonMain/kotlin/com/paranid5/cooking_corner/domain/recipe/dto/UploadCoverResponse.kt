package com.paranid5.cooking_corner.domain.recipe.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadCoverResponse(@SerialName("file_name") val fileName: String)
