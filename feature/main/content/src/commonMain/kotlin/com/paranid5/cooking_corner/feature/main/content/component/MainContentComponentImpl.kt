package com.paranid5.cooking_corner.feature.main.content.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.feature.main.home.component.HomeComponent
import kotlinx.coroutines.flow.StateFlow

internal class MainContentComponentImpl(
    componentContext: ComponentContext,
    private val homeComponentFactory: HomeComponent.Factory,
    private val onBack: () -> Unit,
) : MainContentComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<MainContentConfig>()

    override val stack: StateFlow<ChildStack<MainContentConfig, MainContentChild>> = childStack(
        source = navigation,
        serializer = MainContentConfig.serializer(),
        initialConfiguration = MainContentConfig.Search,
        handleBackButton = true,
        childFactory = ::createChild,
    ).toStateFlow()

    override fun onUiIntent(intent: MainContentUiIntent) {
        when (intent) {
            is MainContentUiIntent.ShowSearch -> navigation.bringToFront(MainContentConfig.Search)
            is MainContentUiIntent.ShowHome -> navigation.bringToFront(MainContentConfig.Home)
            is MainContentUiIntent.ShowProfile -> navigation.bringToFront(MainContentConfig.Profile)
        }
    }

    private fun createChild(config: MainContentConfig, componentContext: ComponentContext) =
        when (config) {
            is MainContentConfig.Home -> MainContentChild.Home(
                component = buildHomeComponent(config, componentContext)
            )

            is MainContentConfig.Profile -> MainContentChild.Profile // TODO: Profile component

            is MainContentConfig.Search -> MainContentChild.Search // TODO: Search component
        }

    private fun buildHomeComponent(
        config: MainContentConfig.Home,
        componentContext: ComponentContext,
    ) = homeComponentFactory.create(
        componentContext = componentContext,
        onBack = navigation::pop,
    )

    class Factory(
        private val homeComponentFactory: HomeComponent.Factory,
    ) : MainContentComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): MainContentComponent = MainContentComponentImpl(
            componentContext = componentContext,
            homeComponentFactory = homeComponentFactory,
            onBack = onBack,
        )
    }
}