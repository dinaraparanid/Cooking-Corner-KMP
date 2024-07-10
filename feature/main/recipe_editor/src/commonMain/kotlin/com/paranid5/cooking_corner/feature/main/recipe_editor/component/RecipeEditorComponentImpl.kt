package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.paranid5.cooking_corner.component.getComponentStore
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent.BackResult
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.utils.doNothing
import kotlinx.coroutines.ExperimentalCoroutinesApi

internal class RecipeEditorComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: RecipeEditorStoreProvider.Factory,
    private val onBack: (BackResult) -> Unit,
) : RecipeEditorComponent, ComponentContext by componentContext {
    private val componentStore = getComponentStore(
        defaultState = State(),
        storeFactory = { storeFactory.create().provide(initialState = it) }
    )

    private val store = componentStore.value

    @OptIn(ExperimentalCoroutinesApi::class)
    override val stateFlow = store.stateFlow

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            store.labels bindTo ::onLabel
        }
    }

    override fun onUiIntent(intent: UiIntent) = store.accept(intent)

    private fun onLabel(label: Label) = when (label) {
        is Label.Back -> onBack(BackResult.Dismiss)
    }

    class Factory(
        private val storeFactory: RecipeEditorStoreProvider.Factory,
    ) : RecipeEditorComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: (BackResult) -> Unit,
        ): RecipeEditorComponent = RecipeEditorComponentImpl(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onBack = onBack,
        )
    }
}