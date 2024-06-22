package com.paranid5.cooking_corner.feature.main.content.component

interface MainContentUiIntent {
    data object ShowSearch : MainContentUiIntent
    data object ShowHome : MainContentUiIntent
    data object ShowProfile : MainContentUiIntent
}