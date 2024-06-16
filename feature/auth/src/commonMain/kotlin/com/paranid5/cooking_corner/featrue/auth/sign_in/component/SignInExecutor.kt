package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStoreProvider.Msg

internal class SignInExecutor(
    private val authApi: AuthApi,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) = when (intent) {
        is UiIntent.Back -> publish(Label.Back)
        is UiIntent.ConfirmCredentials -> checkCredentials()
        is UiIntent.ShowSignUp -> publish(Label.ShowSignUp)
        is UiIntent.UpdateLoginText -> dispatch(Msg.UpdateLoginText(intent.login))
        is UiIntent.UpdatePasswordText -> dispatch(Msg.UpdatePasswordText(intent.password))
    }

    private fun checkCredentials() {
        // TODO: check credentials
        publish(Label.ConfirmedCredentials)
    }
}