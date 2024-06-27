package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStoreProvider.Msg

internal object SignUpReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg) = when (msg) {
        is Msg.UpdateLoginText ->
            copy(login = msg.login)

        is Msg.UpdatePasswordText ->
            copy(password = msg.password, isPasswordInvalid = false)

        is Msg.UpdatePasswordVisibility ->
            copy(isPasswordVisible = isPasswordVisible.not())

        is Msg.UpdateConfirmPasswordText ->
            copy(confirmPassword = msg.confirmPassword, isPasswordInvalid = false)

        is Msg.InvalidPassword ->
            copy(isPasswordInvalid = true)
    }
}