package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.TokenInteractor
import com.paranid5.cooking_corner.domain.auth.TokenInteractor.TokenResult
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStoreProvider.Msg
import kotlinx.coroutines.launch

internal class SignInExecutor(
    private val authRepository: AuthRepository,
    private val globalEventRepository: GlobalEventRepository,
    private val tokenInteractor: TokenInteractor,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.Back -> publish(Label.Back)

            is UiIntent.ConfirmCredentials -> scope.launch {
                tryAcquireTokens(unhandledErrorSnackbar = intent.unhandledErrorSnackbarMessage)
            }

            is UiIntent.ShowSignUp -> publish(Label.ShowSignUp)

            is UiIntent.UpdateLoginText -> dispatch(Msg.UpdateLoginText(intent.login))

            is UiIntent.UpdatePasswordText -> dispatch(Msg.UpdatePasswordText(intent.password))

            is UiIntent.UpdatePasswordVisibility -> dispatch(Msg.UpdatePasswordVisibility)
        }
    }

    private suspend fun tryAcquireTokens(unhandledErrorSnackbar: SnackbarMessage) = when (
        tokenInteractor.tryAcquireTokens(
            login = state().login,
            password = state().password,
        )
    ) {
        is TokenResult.CredentialsMissing -> error("Credentials missing")

        is TokenResult.InvalidPassword -> dispatch(Msg.InvalidPassword)

        is TokenResult.Success -> {
            authRepository.storeLogin(state().login)
            authRepository.storePassword(state().password)
            publish(Label.ConfirmedCredentials)
        }

        is TokenResult.UnhandledError ->
            globalEventRepository.sendSnackbar(unhandledErrorSnackbar)
    }
}