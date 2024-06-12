package com.paranid5.cooking_corner.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RootComponentImpl(componentContext: ComponentContext) :
    RootComponent,
    ComponentContext by componentContext {
    private val navigation = StackNavigation<RootConfig>()

    private val _stateFlow = MutableStateFlow(RootState())
    override val stateFlow = _stateFlow.asStateFlow()

    override val stack: Value<ChildStack<RootConfig, RootChild>> = childStack(
        source = navigation,
        serializer = RootConfig.serializer(),
        initialConfiguration = RootConfig.Main,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: RootConfig, componentContext: ComponentContext) =
        when (config) {
            RootConfig.Main -> RootChild.Main
        }

    override fun onUiIntent(intent: RootUiIntent) = when (intent) {
        else -> Unit // TODO: UiIntents
    }

    class Factory : RootComponent.Factory {
        override fun create(componentContext: ComponentContext): RootComponent =
            RootComponentImpl(componentContext = componentContext)
    }
}