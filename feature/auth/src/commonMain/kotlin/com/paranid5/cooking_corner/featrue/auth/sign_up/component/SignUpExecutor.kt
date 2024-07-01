package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import arrow.core.Either
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStoreProvider.Msg
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SignUpExecutor(
    private val authApi: AuthApi,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.Back -> publish(Label.Back)

            is UiIntent.ConfirmCredentials -> scope.launch { checkCredentials() }

            is UiIntent.UpdateLoginText -> dispatch(Msg.UpdateLoginText(intent.login))

            is UiIntent.UpdatePasswordText -> dispatch(Msg.UpdatePasswordText(intent.password))

            is UiIntent.UpdatePasswordVisibility -> dispatch(Msg.UpdatePasswordVisibility)

            is UiIntent.UpdateConfirmPasswordText ->
                dispatch(Msg.UpdateConfirmPasswordText(intent.confirmPassword))
        }
    }

    private suspend fun checkCredentials() = when (
        val registerRes = withContext(AppDispatchers.Data) {
            authApi.register(
                username = state().login,
                password = state().password,
            )
        }
    ) {
        is Either.Left -> {
            registerRes.value.printStackTrace()
            dispatch(Msg.DismissErrorDialog)
        }

        is Either.Right -> when (registerRes) {
            is Either.Left -> dispatch(Msg.InvalidCredentials)
            is Either.Right -> publish(Label.ConfirmedCredentials)
        }
    }
}