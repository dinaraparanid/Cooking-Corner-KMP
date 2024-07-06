package com.paranid5.cooking_corner.feature.main.generate.component

sealed interface GenerateUiIntent {
    data object Back : GenerateUiIntent
    data class UpdateLink(val link: String) : GenerateUiIntent
    data object GenerateClick : GenerateUiIntent
}