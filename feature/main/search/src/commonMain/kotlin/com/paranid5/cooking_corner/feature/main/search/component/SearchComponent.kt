package com.paranid5.cooking_corner.feature.main.search.component

import com.arkivanov.decompose.ComponentContext

interface SearchComponent {
    interface Factory {
        fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): SearchComponent
    }
}