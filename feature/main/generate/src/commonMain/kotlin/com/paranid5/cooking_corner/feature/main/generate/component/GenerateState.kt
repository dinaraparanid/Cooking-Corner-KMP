package com.paranid5.cooking_corner.feature.main.generate.component

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class GenerateState(val link: String) {
    constructor() : this(link = "")
}