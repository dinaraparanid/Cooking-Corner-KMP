package com.paranid5.cooking_corner.featrue.auth.sign_in.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.paranid5.cooking_corner.component.getComponentStore
import com.paranid5.cooking_corner.domain.auth.AuthApi
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInComponent.BackResult
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.Label
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInStore.UiIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi

internal class SignInComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: SignInStoreProvider.Factory,
    private val onBack: (BackResult) -> Unit,
) : SignInComponent,
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
        is Label.ConfirmedCredentials -> onBack(BackResult.SignedIn)
        is Label.ShowSignUp -> onBack(BackResult.ShowSignUp)
    }

    class Factory(private val storeFactory: SignInStoreProvider.Factory) : SignInComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): SignInComponent = SignInComponentImpl(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onBack = onBack,
        )
    }
}