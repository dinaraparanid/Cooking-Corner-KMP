package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStoreProvider.Msg

internal class SignUpExecutor(
    private val authApi: AuthApi,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) = when (intent) {
        is UiIntent.Back ->
            publish(Label.Back)

        is UiIntent.ConfirmCredentials ->
            checkCredentials()

        is UiIntent.UpdateLoginText ->
            dispatch(Msg.UpdateLoginText(intent.login))

        is UiIntent.UpdatePasswordText ->
            dispatch(Msg.UpdatePasswordText(intent.password))

        is UiIntent.UpdateConfirmPasswordText ->
            dispatch(Msg.UpdateConfirmPasswordText(intent.confirmPassword))
    }

    private fun checkCredentials() {
        // TODO: check credentials
        publish(Label.ConfirmedCredentials)
    }
}