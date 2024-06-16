package com.paranid5.cooking_corner.featrue.auth.component

sealed interface AuthUiIntent {
    data object ShowSignIn : AuthUiIntent
    data object ShowSignUp : AuthUiIntent
}