package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.category.CategoryRepository
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendLogOut
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.domain.recipe.RecipeRepository
import com.paranid5.cooking_corner.domain.recipe.dto.RecipeModifyParams
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent.Factory.LaunchMode
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.Label
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.State
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStore.UiIntent
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorStoreProvider.Msg
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.RecipeParamsUiState
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.ui.entity.mappers.fromResponse
import com.paranid5.cooking_corner.ui.entity.mappers.toRequest
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.handleApiResult
import com.paranid5.cooking_corner.utils.mapToImmutableList
import com.paranid5.cooking_corner.utils.toIntOrZero
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class RecipeEditorExecutor(
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository,
    private val globalEventRepository: GlobalEventRepository,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.Back -> publish(Label.Back)

            is UiIntent.Load -> loadRecipe(recipeId = intent.recipeId)

            is UiIntent.Save -> scope.launch {
                onSave(
                    launchMode = intent.launchMode,
                    unhandledErrorSnackbar = intent.unhandledErrorSnackbar,
                    successSnackbar = intent.successSnackbar,
                )
            }

            is UiIntent.UpdateUiState ->
                dispatch(Msg.UpdateUiState(intent.uiState))

            is UiIntent.UpdateCarbohydrates ->
                dispatch(Msg.UpdateCarbohydrates(intent.carbohydratesInput))

            is UiIntent.UpdateCookingTime ->
                dispatch(Msg.UpdateCookingTime(intent.cookingTimeInput))

            is UiIntent.UpdateDescription ->
                dispatch(Msg.UpdateDescription(intent.description))

            is UiIntent.UpdateDishes ->
                dispatch(Msg.UpdateDishes(intent.dishesInput))

            is UiIntent.UpdateFats ->
                dispatch(Msg.UpdateFats(intent.fatsInput))

            is UiIntent.UpdateName ->
                dispatch(Msg.UpdateName(intent.name))

            is UiIntent.UpdateNutritions ->
                dispatch(Msg.UpdateNutritions(intent.nutritionsInput))

            is UiIntent.UpdatePortions ->
                dispatch(Msg.UpdatePortions(intent.portionsInput))

            is UiIntent.UpdateComments ->
                dispatch(Msg.UpdateComments(intent.commentsInput))

            is UiIntent.UpdatePreparationTime ->
                dispatch(Msg.UpdatePreparationTime(intent.preparationTimeInput))

            is UiIntent.UpdateProteins ->
                dispatch(Msg.UpdateProteins(intent.proteinsInput))

            is UiIntent.UpdateRestTime ->
                dispatch(Msg.UpdateRestTime(intent.restTimeInput))

            is UiIntent.UpdateSelectedCategory ->
                dispatch(Msg.UpdateSelectedCategory(intent.index))

            is UiIntent.UpdateSource ->
                dispatch(Msg.UpdateSource(intent.source))

            is UiIntent.UpdateCover ->
                dispatch(Msg.UpdateCover(ImageContainer.Bytes(intent.cover)))

            is UiIntent.UpdateVideoLink ->
                dispatch(Msg.UpdateVideoLink(intent.videoLink))

            is UiIntent.Ingredient -> executeIngredientIntent(intent)

            is UiIntent.Step -> executeStepIntent(intent)
        }
    }

    private fun executeIngredientIntent(intent: UiIntent.Ingredient) = when (intent) {
        is UiIntent.Ingredient.Add -> {
            val ingredient = state().ingredientDialogState.inputIngredientUiState
            dispatch(Msg.Ingredient.Add(ingredient))
            dispatch(Msg.Ingredient.UpdateDialogState())
        }

        is UiIntent.Ingredient.Remove ->
            dispatch(Msg.Ingredient.Remove(intent.ingredient))

        is UiIntent.Ingredient.UpdatePortion ->
            dispatch(Msg.Ingredient.UpdatePortion(intent.portion))

        is UiIntent.Ingredient.UpdateTitle ->
            dispatch(Msg.Ingredient.UpdateTitle(intent.title))

        is UiIntent.Ingredient.UpdateDialogState ->
            dispatch(Msg.Ingredient.UpdateDialogState(intent.ingredientDialogState))
    }

    private fun executeStepIntent(intent: UiIntent.Step) = when (intent) {
        is UiIntent.Step.Add -> {
            val step = state().stepDialogState.inputStepUiState
            dispatch(Msg.Step.Add(step))
            dispatch(Msg.Step.UpdateDialogState())
        }

        is UiIntent.Step.Remove ->
            dispatch(Msg.Step.Remove(intent.step))

        is UiIntent.Step.UpdateDescription ->
            dispatch(Msg.Step.UpdateDescription(intent.description))

        is UiIntent.Step.UpdateCover ->
            dispatch(Msg.Step.UpdateCover(intent.cover))

        is UiIntent.Step.UpdateTitle ->
            dispatch(Msg.Step.UpdateTitle(intent.title))

        is UiIntent.Step.UpdateDialogState ->
            dispatch(Msg.Step.UpdateDialogState(dialogState = intent.stepDialogState))
    }

    override fun executeAction(action: Unit) = loadCategories()

    // -------------------- UI Intent handling --------------------

    private fun loadRecipe(recipeId: Long) {
        dispatch(Msg.UpdateUiState(UiState.Loading))

        scope.launch {
            handleApiResult(
                result = withContext(AppDispatchers.Data) {
                    recipeRepository.getRecipeById(recipeId = recipeId)
                },
                onUnhandledError = { dispatch(Msg.UpdateUiState(UiState.Error())) },
                onErrorStatusCode = { status ->
                    when {
                        status.isForbidden -> globalEventRepository.sendLogOut(Reason.ERROR)
                        else -> dispatch(Msg.UpdateUiState(UiState.Error()))
                    }
                }
            ) {
                val recipeUiState = RecipeParamsUiState.fromResponse(response = it)
                dispatch(Msg.UpdateUiState(UiState.Success, recipeUiState))
            }
        }
    }

    private fun loadCategories() {
        dispatch(Msg.UpdateCategoriesUiState(UiState.Loading))

        scope.launch {
            handleApiResult(
                result = withContext(AppDispatchers.Data) { categoryRepository.getAll() },
                onUnhandledError = { dispatch(Msg.UpdateCategoriesUiState(it.toUiState())) },
                onErrorStatusCode = { dispatch(Msg.UpdateCategoriesUiState(UiState.Error())) },
            ) { categories ->
                dispatch(
                    Msg.UpdateCategoriesUiState(
                        withContext(AppDispatchers.Eval) {
                            categories
                                .mapToImmutableList(::CategoryUiState)
                                .let(::SerializableImmutableList)
                                .toUiState()
                        }
                    )
                )
            }
        }
    }

    private suspend fun onSave(
        launchMode: LaunchMode,
        unhandledErrorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) {
        val isNameEmpty = state().isNameEmpty
        val isCategoryNotSelected = state().isCategorySelected.not()

        if (isNameEmpty || isCategoryNotSelected) {
            if (isNameEmpty) dispatch(Msg.ShowNameEmptyError)
            if (isCategoryNotSelected) dispatch(Msg.ShowCategoryEmptyError)
            return
        }

        when (launchMode) {
            is LaunchMode.Edit -> edit(
                unhandledErrorSnackbar = unhandledErrorSnackbar,
                successSnackbar = successSnackbar,
            )

            is LaunchMode.New, is LaunchMode.Generate -> create(
                unhandledErrorSnackbar = unhandledErrorSnackbar,
                successSnackbar = successSnackbar,
            )
        }
    }

    private suspend fun create(
        unhandledErrorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) {
        val ingredientsJob = scope.async(AppDispatchers.Eval) {
            state().recipeParamsUiState.ingredients.map(IngredientUiState::toRequest)
        }

        val stepsJob = scope.async(AppDispatchers.Eval) {
            state().recipeParamsUiState.steps.map(StepUiState::toRequest)
        }

        val resultJob = scope.async(AppDispatchers.Data) {
            state().run {
                recipeRepository.create(
                    RecipeModifyParams(
                        name = recipeParamsUiState.name,
                        description = recipeParamsUiState.description,
                        iconPath = null,
                        category = selectedCategoryTitleOrNull,
                        preparingTime = preparationTimeMinutes,
                        cookingTime = cookingTimeMinutes,
                        waitingTime = restTimeMinutes,
                        totalTime = totalTimeMinutes,
                        portions = recipeParamsUiState.portionsInput.toIntOrZero(),
                        comments = recipeParamsUiState.commentsInput,
                        nutritions = recipeParamsUiState.nutritionsInput.toIntOrZero(),
                        proteins = recipeParamsUiState.proteinsInput.toIntOrZero(),
                        fats = recipeParamsUiState.fatsInput.toIntOrZero(),
                        carbohydrates = recipeParamsUiState.carbohydratesInput.toIntOrZero(),
                        dishes = recipeParamsUiState.dishesInput,
                        videoLink = recipeParamsUiState.videoLink,
                        source = recipeParamsUiState.source,
                        ingredients = ingredientsJob.await(),
                        steps = stepsJob.await(),
                    )
                )
            }
        }

        handleModifyApiResult(
            result = resultJob.await(),
            unhandledErrorSnackbar = unhandledErrorSnackbar,
        ) {
            storeCoverOrFinish(
                unhandledErrorSnackbar = unhandledErrorSnackbar,
                successSnackbar = successSnackbar,
            )
        }
    }

    private suspend fun edit(
        unhandledErrorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) {
        val ingredientsJob = scope.async(AppDispatchers.Eval) {
            state().recipeParamsUiState.ingredients.map(IngredientUiState::toRequest)
        }

        val stepsJob = scope.async(AppDispatchers.Eval) {
            state().recipeParamsUiState.steps.map(StepUiState::toRequest)
        }

        val resultJob = scope.async(AppDispatchers.Data) {
            state().run {
                recipeRepository.update(
                    recipeId = requireNotNull(state().recipeParamsUiState.id),
                    RecipeModifyParams(
                        name = recipeParamsUiState.name,
                        description = recipeParamsUiState.description,
                        iconPath = null,
                        category = selectedCategoryTitleOrNull,
                        preparingTime = preparationTimeMinutes,
                        cookingTime = cookingTimeMinutes,
                        waitingTime = restTimeMinutes,
                        totalTime = totalTimeMinutes,
                        portions = recipeParamsUiState.portionsInput.toIntOrZero(),
                        comments = recipeParamsUiState.commentsInput,
                        nutritions = recipeParamsUiState.nutritionsInput.toIntOrZero(),
                        proteins = recipeParamsUiState.proteinsInput.toIntOrZero(),
                        fats = recipeParamsUiState.fatsInput.toIntOrZero(),
                        carbohydrates = recipeParamsUiState.carbohydratesInput.toIntOrZero(),
                        dishes = recipeParamsUiState.dishesInput,
                        videoLink = recipeParamsUiState.videoLink,
                        source = recipeParamsUiState.source,
                        ingredients = ingredientsJob.await(),
                        steps = stepsJob.await(),
                    )
                )
            }
        }

        handleModifyApiResult(
            result = resultJob.await(),
            unhandledErrorSnackbar = unhandledErrorSnackbar,
        ) {
            storeCoverOrFinish(
                unhandledErrorSnackbar = unhandledErrorSnackbar,
                successSnackbar = successSnackbar,
            )
        }
    }

    private suspend fun uploadRecipeCover(
        recipeCover: ByteArray,
        unhandledErrorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) = handleModifyApiResult(
        unhandledErrorSnackbar = unhandledErrorSnackbar,
        result = withContext(AppDispatchers.Data) {
            recipeRepository.uploadRecipeCover(cover = recipeCover)
        },
        onSuccess = { onSuccess(successSnackbar) }
    )

    private suspend fun storeCoverOrFinish(
        unhandledErrorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) {
        (state().recipeParamsUiState.cover as? ImageContainer.Bytes?)?.value?.let {
            uploadRecipeCover(
                recipeCover = it,
                unhandledErrorSnackbar = unhandledErrorSnackbar,
                successSnackbar = successSnackbar
            )
        } ?: onSuccess(successSnackbar = successSnackbar)
    }

    private suspend inline fun onSuccess(successSnackbar: SnackbarMessage) {
        showSnackbar(successSnackbar)
        publish(Label.Back)
    }

    // -------------------- API results handling --------------------

    private suspend inline fun handleModifyApiResult(
        result: ApiResultWithCode<Unit>,
        unhandledErrorSnackbar: SnackbarMessage,
        onSuccess: () -> Unit,
    ) = handleApiResult(
        result = result,
        onUnhandledError = { showSnackbar(unhandledErrorSnackbar) },
        onErrorStatusCode = { showSnackbar(unhandledErrorSnackbar) },
        onSuccess = { onSuccess() },
    )

    private suspend fun showSnackbar(snackbarMessage: SnackbarMessage) =
        globalEventRepository.sendSnackbar(snackbarMessage)
}
