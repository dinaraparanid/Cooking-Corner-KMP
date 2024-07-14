package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeParamsUiState

interface RecipeEditorComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    sealed interface BackResult {
        data object Dismiss : BackResult
        data class Uploaded(val snackbarMessage: SnackbarMessage) : BackResult
    }

    interface Factory {
        sealed interface LaunchMode {
            data object New : LaunchMode
            data class Edit(val recipeId: Long) : LaunchMode
            data class Generate(val recipeParamsUiState: RecipeParamsUiState) : LaunchMode
        }

        fun create(
            componentContext: ComponentContext,
            launchMode: LaunchMode,
            onBack: (BackResult) -> Unit,
        ): RecipeEditorComponent
    }
}
