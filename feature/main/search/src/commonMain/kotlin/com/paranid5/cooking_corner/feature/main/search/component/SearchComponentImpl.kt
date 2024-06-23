package com.paranid5.cooking_corner.feature.main.search.component

import com.arkivanov.decompose.ComponentContext

internal class SearchComponentImpl(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
) : SearchComponent, ComponentContext by componentContext {
    class Factory : SearchComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): SearchComponent = SearchComponentImpl(
            componentContext = componentContext,
            onBack = onBack,
        )
    }
}