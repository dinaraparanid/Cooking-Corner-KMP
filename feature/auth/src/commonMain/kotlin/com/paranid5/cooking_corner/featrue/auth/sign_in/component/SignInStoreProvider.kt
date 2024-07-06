package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.TokenInteractor
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent

internal class SignInStoreProvider(
    private val storeFactory: StoreFactory,
    private val authRepository: AuthRepository,
    private val tokenInteractor: TokenInteractor,
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
                    authRepository = authRepository,
                    tokenInteractor = tokenInteractor,
                )
            },
            reducer = SignInReducer,
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val authRepository: AuthRepository,
        private val tokenInteractor: TokenInteractor,
    ) {
        fun create() = SignInStoreProvider(
            storeFactory = storeFactory,
            authRepository = authRepository,
            tokenInteractor = tokenInteractor,
        )
    }
}