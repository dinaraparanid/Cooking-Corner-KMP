package com.paranid5.cooking_corner.feature.main.generate.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.getComponentState
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateComponent.BackResult
import com.paranid5.cooking_corner.utils.doNothing
import com.paranid5.cooking_corner.utils.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class GenerateComponentImpl(
    componentContext: ComponentContext,
    private val onBack: (result: BackResult) -> Unit,
) : GenerateComponent, ComponentContext by componentContext {
    private val componentState = getComponentState(defaultState = GenerateState())

    private val _stateFlow = MutableStateFlow(componentState.value)
    override val stateFlow = _stateFlow.asStateFlow()

    override fun onUiIntent(intent: GenerateUiIntent) {
        when (intent) {
            is GenerateUiIntent.Back -> onBack(BackResult.Dismiss)
            is GenerateUiIntent.GenerateClick -> doNothing // TODO: on generate click
            is GenerateUiIntent.UpdateLink -> _stateFlow.updateState { copy(link = intent.link) }
        }
    }

    class Factory : GenerateComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: (result: BackResult) -> Unit,
        ): GenerateComponent = GenerateComponentImpl(
            componentContext = componentContext,
            onBack = onBack,
        )
    }
}