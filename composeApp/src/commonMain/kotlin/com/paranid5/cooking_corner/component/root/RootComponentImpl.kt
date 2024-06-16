package com.paranid5.cooking_corner.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.domain.auth.AuthDataSource
import com.paranid5.cooking_corner.featrue.auth.component.AuthComponent
import com.paranid5.cooking_corner.feature.main.root.MainRootComponent
import com.paranid5.cooking_corner.feature.splash.component.SplashScreenComponent
import kotlinx.coroutines.flow.StateFlow

internal class RootComponentImpl(
    componentContext: ComponentContext,
    private val authDataSource: AuthDataSource,
    private val splashScreenComponentFactory: SplashScreenComponent.Factory,
    private val authComponentFactory: AuthComponent.Factory,
    private val mainRootComponentFactory: MainRootComponent.Factory,
) : RootComponent,
    ComponentContext by componentContext {
    private val navigation = StackNavigation<RootConfig>()

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
                buildSplashScreenComponent(componentContext)
            )

            RootConfig.Auth -> RootChild.Auth(
                buildAuthComponent(componentContext)
            )

            RootConfig.Main -> RootChild.Main(
                buildMainComponent(componentContext)
            )
        }

    private fun buildSplashScreenComponent(componentContext: ComponentContext) =
        splashScreenComponentFactory.create(
            componentContext = componentContext,
            onSplashScreenClosed = {
                // TODO: handle auth
                navigation.replaceCurrent(RootConfig.Auth)
            }
        )

    private fun buildAuthComponent(componentContext: ComponentContext) =
        authComponentFactory.create(
            componentContext = componentContext,
            onBack = { result ->
                when (result) {
                    is AuthComponent.BackResult.Dismiss ->
                        navigation.pop()

                    is AuthComponent.BackResult.LoggedIn ->
                        navigation.replaceCurrent(RootConfig.Main)
                }
            },
        )

    private fun buildMainComponent(componentContext: ComponentContext) =
        mainRootComponentFactory.create(
            componentContext = componentContext,
            onBack = navigation::pop,
        )

    internal class Factory(
        private val authDataSource: AuthDataSource,
        private val splashScreenComponentFactory: SplashScreenComponent.Factory,
        private val authComponentFactory: AuthComponent.Factory,
        private val mainRootComponentFactory: MainRootComponent.Factory,
    ) : RootComponent.Factory {
        override fun create(componentContext: ComponentContext): RootComponent =
            RootComponentImpl(
                componentContext = componentContext,
                authDataSource = authDataSource,
                splashScreenComponentFactory = splashScreenComponentFactory,
                authComponentFactory = authComponentFactory,
                mainRootComponentFactory = mainRootComponentFactory,
            )
    }
}