package com.paranid5.cooking_corner.featrue.auth.sign_up.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.paranid5.cooking_corner.component.getComponentStore
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpComponent.BackResult
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi

internal class SignUpComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: SignUpStoreProvider.Factory,
    private val authApi: AuthApi,
    private val onBack: (BackResult) -> Unit,
) : SignUpComponent,
    ComponentContext by componentContext {
    private val componentStore = getComponentStore(
        defaultState = State(),
        storeFactory = { storeFactory.create().provide(initialState = it) }
    )

    private val store = componentStore.value

    @OptIn(ExperimentalCoroutinesApi::class)
    override val stateFlow = store.stateFlow

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            store.labels bindTo ::onLabel
        }
    }

    override fun onUiIntent(intent: UiIntent) = store.accept(intent)

    private fun onLabel(label: Label) = when (label) {
        is Label.Back -> onBack(BackResult.Dismiss)
        is Label.ConfirmedCredentials -> onBack(BackResult.SignedUp)
    }

    class Factory(
        private val storeFactory: SignUpStoreProvider.Factory,
        private val authApi: AuthApi,
    ) : SignUpComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): SignUpComponent = SignUpComponentImpl(
            componentContext = componentContext,
            storeFactory = storeFactory,
            authApi = authApi,
            onBack = onBack,
        )
    }
}