package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import arrow.core.Either
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.domain.auth.AuthDataSource
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStoreProvider.Msg
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SignInExecutor(
    private val authApi: AuthApi,
    private val authDataSource: AuthDataSource,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.Back -> publish(Label.Back)

            is UiIntent.ConfirmCredentials -> scope.launch { checkCredentials() }

            is UiIntent.ShowSignUp -> publish(Label.ShowSignUp)

            is UiIntent.UpdateLoginText -> dispatch(Msg.UpdateLoginText(intent.login))

            is UiIntent.UpdatePasswordText -> dispatch(Msg.UpdatePasswordText(intent.password))

            is UiIntent.UpdatePasswordVisibility -> dispatch(Msg.UpdatePasswordVisibility)
        }
    }

    private suspend fun checkCredentials() = when (
        val loginRes = withContext(AppDispatchers.Data) {
            authApi.login(
                username = state().login,
                password = state().password,
            )
        }
    ) {
        is Either.Left -> {
            loginRes.value.printStackTrace()
            dispatch(Msg.UpdateErrorDialogVisibility(isVisible = true))
        }

        is Either.Right -> when (val res = loginRes.value) {
            is Either.Left -> dispatch(Msg.InvalidPassword)

            is Either.Right -> {
                authDataSource.storeAccessToken(accessToken = res.value.accessToken)
                authDataSource.storeRefreshToken(refreshToken = res.value.refreshToken)
                publish(Label.ConfirmedCredentials)
            }
        }
    }
}