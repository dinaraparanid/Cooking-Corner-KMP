package com.paranid5.cooking_corner.feature.main.root.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.feature.main.content.component.MainContentComponent
import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponent.AuthorizeType
import com.paranid5.cooking_corner.feature.main.splash.component.MainSplashScreenComponent
import kotlinx.coroutines.flow.StateFlow

internal class MainRootComponentImpl(
    componentContext: ComponentContext,
    private val mainSplashScreenComponentFactory: MainSplashScreenComponent.Factory,
    private val mainContentComponentFactory: MainContentComponent.Factory,
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
        AuthorizeType.SIGNED_IN -> MainRootConfig.Content
    }

    private fun createChild(config: MainRootConfig, componentContext: ComponentContext) =
        when (config) {
            is MainRootConfig.SplashScreen -> MainRootChild.SplashScreen(
                component = buildMainSplashScreenComponent(componentContext)
            )

            is MainRootConfig.Content -> MainRootChild.Content(
                component = buildMainContentComponent(componentContext)
            )
        }

    private fun buildMainSplashScreenComponent(componentContext: ComponentContext) =
        mainSplashScreenComponentFactory.create(componentContext = componentContext) {
            navigation.replaceCurrent(MainRootConfig.Content)
        }

    private fun buildMainContentComponent(componentContext: ComponentContext) =
        mainContentComponentFactory.create(
            componentContext = componentContext,
            onBack = navigation::pop,
        )

    class Factory(
        private val mainSplashScreenComponentFactory: MainSplashScreenComponent.Factory,
        private val mainContentComponentFactory: MainContentComponent.Factory,
    ) : MainRootComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            authType: AuthorizeType,
            onBack: () -> Unit,
        ): MainRootComponent = MainRootComponentImpl(
            componentContext = componentContext,
            mainSplashScreenComponentFactory = mainSplashScreenComponentFactory,
            mainContentComponentFactory = mainContentComponentFactory,
            authType = authType,
            onBack = onBack,
        )
    }
}