package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import kotlinx.serialization.Serializable

internal interface SignInStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data object Back : UiIntent
        data class UpdateLoginText(val login: String) : UiIntent
        data class UpdatePasswordText(val password: String) : UiIntent
        data object UpdatePasswordVisibility : UiIntent
        data object ConfirmCredentials : UiIntent
        data object ShowSignUp : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val login: String,
        val password: String,
        val isPasswordVisible: Boolean,
    ) {
        constructor() : this(
            login = "",
            password = "",
            isPasswordVisible = false,
        )
    }

    sealed interface Label {
        data object Back : Label
        data object ConfirmedCredentials : Label
        data object ShowSignUp : Label
    }
}