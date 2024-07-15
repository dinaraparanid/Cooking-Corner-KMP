package com.paranid5.cooking_corner.ui.entity

import androidx.compose.runtime.Immutable
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