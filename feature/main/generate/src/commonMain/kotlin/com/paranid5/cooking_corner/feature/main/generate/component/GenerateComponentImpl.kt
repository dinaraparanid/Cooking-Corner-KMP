package com.paranid5.cooking_corner.feature.main.generate.component

import com.arkivanov.decompose.ComponentContext
import com.paranid5.cooking_corner.component.componentScope
import com.paranid5.cooking_corner.component.getComponentState
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendLogOut
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateComponent.BackResult
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeParamsUiState
import com.paranid5.cooking_corner.ui.entity.mappers.fromResponse
import com.paranid5.cooking_corner.utils.api.handleApiResult
import com.paranid5.cooking_corner.utils.updateState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class GenerateComponentImpl(
    componentContext: ComponentContext,
    private val recipeRepository: RecipeRepository,
    private val globalEventRepository: GlobalEventRepository,
    private val onBack: (result: BackResult) -> Unit,
) : GenerateComponent, ComponentContext by componentContext {
    private val componentState = getComponentState(defaultState = GenerateState())

    private val _stateFlow = MutableStateFlow(componentState.value)
    override val stateFlow = _stateFlow.asStateFlow()

    private var generateTask: Job? = null

    override fun onUiIntent(intent: GenerateUiIntent) {
        when (intent) {
            is GenerateUiIntent.Back -> when (generateTask) {
                null -> onBack(BackResult.Dismiss)
                else -> {
                    generateTask?.cancel()
                    generateTask = null
                    _stateFlow.updateState { copy(uiState = UiState.Error()) }
                }
            }

            is GenerateUiIntent.GenerateClick ->
                generate(generationErrorSnackbar = intent.generationErrorSnackbar)

            is GenerateUiIntent.UpdateLink ->
                _stateFlow.updateState { copy(link = intent.link) }
        }
    }

    private fun generate(generationErrorSnackbar: SnackbarMessage) {
        _stateFlow.updateState { copy(uiState = UiState.Loading) }

        generateTask?.cancel()
        generateTask = componentScope.launch {
            handleApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.generate(stateFlow.value.link)
                },
                onUnhandledError = { onGenerationError(generationErrorSnackbar) },
                onErrorStatusCode = { status ->
                    when {
                        status.isForbidden -> globalEventRepository.sendLogOut(Reason.ERROR)
                        else -> onGenerationError(generationErrorSnackbar)
                    }
                }
            ) {
                onBack(BackResult.Generated(RecipeParamsUiState.fromResponse(response = it)))
            }
        }
    }

    private suspend inline fun onGenerationError(generationErrorSnackbar: SnackbarMessage) {
        globalEventRepository.sendSnackbar(generationErrorSnackbar)
        _stateFlow.updateState { copy(uiState = UiState.Error()) }
    }

    class Factory(
        private val recipeRepository: RecipeRepository,
        private val globalEventRepository: GlobalEventRepository,
    ) : GenerateComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: (result: BackResult) -> Unit,
        ): GenerateComponent = GenerateComponentImpl(
            componentContext = componentContext,
            recipeRepository = recipeRepository,
            globalEventRepository = globalEventRepository,
            onBack = onBack,
        )
    }
}
