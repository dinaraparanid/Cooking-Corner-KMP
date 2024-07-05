package com.paranid5.cooking_corner.feature.main.profile.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.paranid5.cooking_corner.component.getComponentState
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.feature.main.profile.entity.ProfileUiState
import com.paranid5.cooking_corner.utils.doNothing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

internal class ProfileComponentImpl(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : ProfileComponent, ComponentContext by componentContext {
    @Serializable
    sealed interface Slot {
        @Serializable
        data object Edit : Slot
    }

    private val componentState = getComponentState(
        defaultState = ProfileState(
            // TODO: acquire from datastore
            profileUiState = ProfileUiState(
                username = "OlgaLove",
                name = "Olga",
                surname = "Ivanova",
                email = "o.ivanova@gmail.com",
                cookingExperience = 5,
            )
        )
    )

    private val _stateFlow = MutableStateFlow(componentState.value)
    override val stateFlow = _stateFlow.asStateFlow()

    private val childSlotNavigation = SlotNavigation<Slot>()

    override val childSlot: StateFlow<ChildSlot<*, ProfileChild>> = childSlot(
        source = childSlotNavigation,
        serializer = Slot.serializer(),
        childFactory = ::createChildSlot,
    ).toStateFlow()

    override fun onUiIntent(intent: ProfileUiIntent) {
        when (intent) {
            is ProfileUiIntent.Edit -> doNothing // TODO: Edit profile
        }
    }

    private fun createChildSlot(
        configuration: Slot,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Slot.Edit -> ProfileChild.Edit
    }

    class Factory : ProfileComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): ProfileComponent = ProfileComponentImpl(
            componentContext = componentContext,
            onBack = onBack,
        )
    }
}