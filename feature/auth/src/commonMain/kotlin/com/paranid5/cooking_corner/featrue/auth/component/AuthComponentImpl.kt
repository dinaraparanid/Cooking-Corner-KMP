package com.paranid5.cooking_corner.featrue.auth.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.featrue.auth.component.AuthComponent.BackResult
import com.paranid5.cooking_corner.featrue.auth.sign_in.component.SignInComponent
import kotlinx.coroutines.flow.StateFlow

internal class AuthComponentImpl(
    componentContext: ComponentContext,
    private val signInComponentFactory: SignInComponent.Factory,
    private val onBack: (BackResult) -> Unit,
) : AuthComponent,
    ComponentContext by componentContext {
    private val navigation = StackNavigation<AuthConfig>()

    override val stack: StateFlow<ChildStack<AuthConfig, AuthChild>> = childStack(
        source = navigation,
        serializer = AuthConfig.serializer(),
        initialConfiguration = AuthConfig.SignIn,
        handleBackButton = true,
        childFactory = ::createChild
    ).toStateFlow()

    override fun onUiIntent(intent: AuthUiIntent) = when (intent) {
        is AuthUiIntent.ShowSignIn -> navigation.bringToFront(AuthConfig.SignIn)
        is AuthUiIntent.ShowSignUp -> navigation.bringToFront(AuthConfig.SignUp)
    }

    private fun createChild(config: AuthConfig, componentContext: ComponentContext) =
        when (config) {
            is AuthConfig.SignIn -> AuthChild.SignIn(buildSignInComponent(componentContext))
            is AuthConfig.SignUp -> AuthChild.SignUp()
        }

    private fun buildSignInComponent(componentContext: ComponentContext) =
        signInComponentFactory.create(
            componentContext = componentContext,
            onBack = { result ->
                when (result) {
                    is SignInComponent.BackResult.Dismiss -> onBack(BackResult.Dismiss)
                    is SignInComponent.BackResult.ShowSignUp -> onUiIntent(AuthUiIntent.ShowSignUp)
                    is SignInComponent.BackResult.SignedIn -> onBack(BackResult.LoggedIn)
                }
            }
        )

    class Factory(
        private val signInComponentFactory: SignInComponent.Factory,
    ) : AuthComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): AuthComponent = AuthComponentImpl(
            componentContext = componentContext,
            signInComponentFactory = signInComponentFactory,
            onBack = onBack,
        )
    }
}