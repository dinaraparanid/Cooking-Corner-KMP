package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.domain.auth.AuthDataSource
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent

internal class SignInStoreProvider(
    private val storeFactory: StoreFactory,
    private val authApi: AuthApi,
    private val authDataSource: AuthDataSource,
) {
    sealed interface Msg {
        data class UpdateLoginText(val login: String) : Msg
        data class UpdatePasswordText(val password: String) : Msg
        data object UpdatePasswordVisibility : Msg
        data object InvalidPassword : Msg
        data class UpdateErrorDialogVisibility(val isVisible: Boolean) : Msg
    }

    fun provide(initialState: State): SignInStore = object :
        SignInStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "SignInStore",
            initialState = initialState,
            executorFactory = {
                SignInExecutor(
                    authApi = authApi,
                    authDataSource = authDataSource,
                )
            },
            reducer = SignInReducer,
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val authApi: AuthApi,
        private val authDataSource: AuthDataSource,
    ) {
        fun create() = SignInStoreProvider(
            storeFactory = storeFactory,
            authApi = authApi,
            authDataSource = authDataSource,
        )
    }
}