package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.StateSource
import com.paranid5.cooking_corner.component.UiIntentHandler
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeParamsUiState
import kotlinx.serialization.Serializable

interface RecipeEditorComponent : StateSource<State>, UiIntentHandler<UiIntent> {
    sealed interface BackResult {
        data object Dismiss : BackResult
        data class Uploaded(val snackbarMessage: SnackbarMessage) : BackResult
    }

    interface Factory {

        @Serializable
        sealed interface LaunchMode {
            @Serializable
            data object New : LaunchMode

            @Serializable
            data class Edit(val recipeId: Long) : LaunchMode

            @Serializable
            data class Generate(val recipeParamsUiState: RecipeParamsUiState) : LaunchMode
        }

        fun create(
            componentContext: ComponentContext,
            launchMode: LaunchMode,
            onBack: (BackResult) -> Unit,
        ): RecipeEditorComponent
    }
}
