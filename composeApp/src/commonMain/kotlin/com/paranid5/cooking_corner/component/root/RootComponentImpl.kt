package com.paranid5.cooking_corner.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.paranid5.cooking_corner.component.splash.SplashScreenComponent
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.domain.auth.AuthDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class RootComponentImpl(
    componentContext: ComponentContext,
    private val authDataSource: AuthDataSource,
    private val splashScreenComponentFactory: SplashScreenComponent.Factory,
) : RootComponent,
    ComponentContext by componentContext {
    private val navigation = StackNavigation<RootConfig>()

    private val _stateFlow = MutableStateFlow(RootState())
    override val stateFlow = _stateFlow.asStateFlow()

    override val stack: StateFlow<ChildStack<RootConfig, RootChild>> = childStack(
        source = navigation,
        serializer = RootConfig.serializer(),
        initialConfiguration = RootConfig.SplashScreen,
        handleBackButton = true,
        childFactory = ::createChild,
    ).toStateFlow()

    private fun createChild(config: RootConfig, componentContext: ComponentContext) =
        when (config) {
            RootConfig.SplashScreen -> RootChild.SplashScreen(
                component = splashScreenComponentFactory.create(
                    componentContext = componentContext,
                    onSplashScreenClosed = {
                        // TODO: handle auth
                        navigation.replaceCurrent(RootConfig.Auth)
                    }
                )
            )

            RootConfig.Auth -> RootChild.Auth

            RootConfig.Home -> RootChild.Home
        }

    override fun onUiIntent(intent: RootUiIntent) = when (intent) {
        else -> Unit // TODO: UiIntents
    }

    internal class Factory(
        private val authDataSource: AuthDataSource,
        private val splashScreenComponentFactory: SplashScreenComponent.Factory,
    ) : RootComponent.Factory {
        override fun create(componentContext: ComponentContext): RootComponent =
            RootComponentImpl(
                componentContext = componentContext,
                authDataSource = authDataSource,
                splashScreenComponentFactory = splashScreenComponentFactory,
            )
    }
}