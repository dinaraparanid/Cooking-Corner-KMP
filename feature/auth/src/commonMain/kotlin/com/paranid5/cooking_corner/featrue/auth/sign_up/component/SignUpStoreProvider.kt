package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent

internal class SignUpStoreProvider(
    private val storeFactory: StoreFactory,
    private val authApi: AuthApi,
) {
    sealed interface Msg {
        data class UpdateLoginText(val login: String) : Msg
        data class UpdatePasswordText(val password: String) : Msg
        data object UpdatePasswordVisibility : Msg
        data class UpdateConfirmPasswordText(val confirmPassword: String) : Msg
        data object InvalidPassword : Msg
    }

    fun provide(initialState: State): SignUpStore = object :
        SignUpStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "SignInStore",
            initialState = initialState,
            executorFactory = { SignUpExecutor(authApi = authApi) },
            reducer = SignUpReducer,
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val authApi: AuthApi,
    ) {
        fun create() = SignUpStoreProvider(
            storeFactory = storeFactory,
            authApi = authApi,
        )
    }
}