package com.paranid5.cooking_corner.feature.main.profile.component

interface ProfileUiIntent {
    data object Refresh : ProfileUiIntent
    data object Edit : ProfileUiIntent
    data object LogOut : ProfileUiIntent
}