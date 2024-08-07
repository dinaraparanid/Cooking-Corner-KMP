package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

internal interface SignInStore : Store<UiIntent, State, Label> {
    private companion object {
        const val MIN_INPUT_LENGTH = 6
    }

    sealed interface UiIntent {
        data object Back : UiIntent

        data class UpdateLoginText(val login: String) : UiIntent

        data class UpdatePasswordText(val password: String) : UiIntent

        data object UpdatePasswordVisibility : UiIntent

        data class ConfirmCredentials(
            val unhandledErrorSnackbarMessage: SnackbarMessage
        ) : UiIntent

        data object ShowSignUp : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val login: String,
        val password: String,
        val isPasswordVisible: Boolean,
        val isPasswordInvalid: Boolean,
    ) {
        @Transient
        val isUsernameShort = login.length < MIN_INPUT_LENGTH

        @Transient
        val isPasswordShort = password.length < MIN_INPUT_LENGTH

        @Transient
        val isInputNotShort = isUsernameShort.not() && isPasswordShort.not()

        constructor() : this(
            login = "",
            password = "",
            isPasswordVisible = false,
            isPasswordInvalid = false,
        )
    }

    sealed interface Label {
        data object Back : Label
        data object ConfirmedCredentials : Label
        data object ShowSignUp : Label

    }
}