package com.paranid5.cooking_corner.feature.main.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponent.AuthorizeType
import com.paranid5.cooking_corner.feature.main.splash.component.MainSplashScreenComponent
import kotlinx.coroutines.flow.StateFlow

internal class MainRootComponentImpl(
    componentContext: ComponentContext,
    private val mainSplashScreenComponentFactory: MainSplashScreenComponent.Factory,
    authType: AuthorizeType,
    private val onBack: () -> Unit,
) : MainRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<MainRootConfig>()

    override val stack: StateFlow<ChildStack<MainRootConfig, MainRootChild>> = childStack(
        source = navigation,
        serializer = MainRootConfig.serializer(),
        initialConfiguration = getInitialConfiguration(authType),
        handleBackButton = true,
        childFactory = ::createChild
    ).toStateFlow()

    private fun getInitialConfiguration(authType: AuthorizeType): MainRootConfig = when (authType) {
        AuthorizeType.SIGNED_UP -> MainRootConfig.SplashScreen
        AuthorizeType.SIGNED_IN -> MainRootConfig.Main
    }

    private fun createChild(config: MainRootConfig, componentContext: ComponentContext) =
        when (config) {
            is MainRootConfig.SplashScreen ->
                MainRootChild.SplashScreen(buildMainSplashScreenComponent(componentContext))

            is MainRootConfig.Main ->
                MainRootChild.Main // TODO: Main component
        }

    private fun buildMainSplashScreenComponent(componentContext: ComponentContext) =
        mainSplashScreenComponentFactory.create(componentContext = componentContext) {
            navigation.replaceCurrent(MainRootConfig.Main)
        }

    class Factory(
        private val mainSplashScreenComponentFactory: MainSplashScreenComponent.Factory,
    ) : MainRootComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            authType: AuthorizeType,
            onBack: () -> Unit,
        ): MainRootComponent = MainRootComponentImpl(
            componentContext = componentContext,
            mainSplashScreenComponentFactory = mainSplashScreenComponentFactory,
            authType = authType,
            onBack = onBack,
        )
    }
}