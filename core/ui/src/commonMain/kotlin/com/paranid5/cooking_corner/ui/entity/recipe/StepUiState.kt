package com.paranid5.cooking_corner.ui.entity.recipe

import androidx.compose.runtime.Immutable
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
@Immutable
data class StepUiState(
    val title: String,
    val description: String,
    val cover: ImageContainer? = null,
    val key: Long = Random.nextLong(),
)