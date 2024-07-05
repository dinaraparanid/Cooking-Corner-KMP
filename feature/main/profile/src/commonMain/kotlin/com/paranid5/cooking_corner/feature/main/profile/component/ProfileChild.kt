package com.paranid5.cooking_corner.feature.main.profile.component

sealed interface ProfileChild {
    data object Edit : ProfileChild
}