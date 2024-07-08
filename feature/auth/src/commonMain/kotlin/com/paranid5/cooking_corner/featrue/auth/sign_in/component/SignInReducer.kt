package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStoreProvider.Msg

internal object SignInReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg) = when (msg) {
        is Msg.UpdateLoginText -> copy(login = msg.login)
        is Msg.UpdatePasswordText -> copy(password = msg.password, isPasswordInvalid = false)
        is Msg.UpdatePasswordVisibility -> copy(isPasswordVisible = isPasswordVisible.not())
        is Msg.InvalidPassword -> copy(isPasswordInvalid = true)
        is Msg.UpdateErrorDialogVisibility -> copy(isErrorDialogVisible = msg.isVisible)
    }
}