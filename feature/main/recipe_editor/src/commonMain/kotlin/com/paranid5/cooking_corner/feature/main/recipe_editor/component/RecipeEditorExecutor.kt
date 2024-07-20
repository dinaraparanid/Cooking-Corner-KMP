package com.paranid5.cooking_corner.feature.main.recipe_editor.component

import arrow.core.raise.nullable
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
import com.paranid5.cooking_corner.ui.entity.recipe.CategoryUiState
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.recipe.IngredientUiState
import com.paranid5.cooking_corner.ui.entity.recipe.RecipeParamsUiState
import com.paranid5.cooking_corner.ui.entity.recipe.StepUiState
import com.paranid5.cooking_corner.ui.entity.mappers.fromResponse
import com.paranid5.cooking_corner.ui.entity.mappers.toRequest
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.ui.utils.SerializableImmutableList
import com.paranid5.cooking_corner.utils.api.ApiResult
import com.paranid5.cooking_corner.utils.api.acquireApiResult
import com.paranid5.cooking_corner.utils.api.getOrNull
import com.paranid5.cooking_corner.utils.doNothing
import com.paranid5.cooking_corner.utils.api.handleApiResult
import com.paranid5.cooking_corner.utils.mapToImmutableList
import com.paranid5.cooking_corner.utils.toIntOrZero
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.toList
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

        dispatch(Msg.UpdateUiState(UiState.Loading))

        uploadCoverAndProceed(unhandledErrorSnackbar = unhandledErrorSnackbar) { recipeCover, stepsCovers ->
            uploadRecipe(
                launchMode = launchMode,
                recipeCoverUrl = recipeCover,
                stepsCoversUrls = stepsCovers,
                unhandledErrorSnackbar = unhandledErrorSnackbar,
                successSnackbar = successSnackbar,
            )
        }
    }

    private suspend fun uploadRecipe(
        launchMode: LaunchMode,
        recipeCoverUrl: String?,
        stepsCoversUrls: List<String?>,
        unhandledErrorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) = when (launchMode) {
        is LaunchMode.Edit -> edit(
            recipeCoverUrl = recipeCoverUrl,
            stepsCoversUrls = stepsCoversUrls,
            unhandledErrorSnackbar = unhandledErrorSnackbar,
            successSnackbar = successSnackbar,
        )

        is LaunchMode.New, is LaunchMode.Generate -> create(
            recipeCoverUrl = recipeCoverUrl,
            stepsCoversUrls = stepsCoversUrls,
            unhandledErrorSnackbar = unhandledErrorSnackbar,
            successSnackbar = successSnackbar,
        )
    }

    private suspend fun create(
        recipeCoverUrl: String?,
        stepsCoversUrls: List<String?>,
        unhandledErrorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) {
        val ingredientsTask = scope.async(AppDispatchers.Eval) {
            buildIngredientsRequests()
        }

        val stepsTask = scope.async(AppDispatchers.Eval) {
            buildStepsRequests(stepsCoversUrls = stepsCoversUrls)
        }

        val resultJob = scope.async(AppDispatchers.Data) {
            state().run {
                recipeRepository.create(
                    RecipeModifyParams(
                        name = recipeParamsUiState.name,
                        description = recipeParamsUiState.description,
                        iconPath = recipeCoverUrl,
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
                        ingredients = ingredientsTask.await(),
                        steps = stepsTask.await(),
                    )
                )
            }
        }

        handleRecipeApiResult(
            result = resultJob.await(),
            unhandledErrorSnackbar = unhandledErrorSnackbar,
            onSuccess = { onSuccess(successSnackbar = successSnackbar) },
        )
    }

    private suspend fun edit(
        recipeCoverUrl: String?,
        stepsCoversUrls: List<String?>,
        unhandledErrorSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) {
        val ingredientsJob = scope.async(AppDispatchers.Eval) {
            buildIngredientsRequests()
        }

        val stepsJob = scope.async(AppDispatchers.Eval) {
            buildStepsRequests(stepsCoversUrls = stepsCoversUrls)
        }

        val resultJob = scope.async(AppDispatchers.Data) {
            state().run {
                recipeRepository.update(
                    recipeId = requireNotNull(state().recipeParamsUiState.id),
                    RecipeModifyParams(
                        name = recipeParamsUiState.name,
                        description = recipeParamsUiState.description,
                        iconPath = recipeCoverUrl,
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

        handleRecipeApiResult(
            result = resultJob.await(),
            unhandledErrorSnackbar = unhandledErrorSnackbar,
            onSuccess = { onSuccess(successSnackbar = successSnackbar) },
        )
    }

    private fun buildIngredientsRequests() =
        state().recipeParamsUiState.ingredients.map(IngredientUiState::toRequest)

    private fun buildStepsRequests(stepsCoversUrls: List<String?>) =
        state()
            .recipeParamsUiState
            .steps
            .map(StepUiState::toRequest)
            .zip(stepsCoversUrls) { step, cover ->
                step.copy(imagePath = cover ?: step.imagePath)
            }

    private suspend inline fun uploadCoverAndProceed(
        unhandledErrorSnackbar: SnackbarMessage,
        then: (recipeCover: String?, stepsCovers: List<String?>) -> Unit,
    ) {
        val steps = state().recipeParamsUiState.steps

        val stepsCoversUrls = steps
            .asFlow()
            .map { (it.cover as? ImageContainer.Bytes?)?.value }
            .map { coverData -> coverData?.let { uploadCover(cover = it) } }
            .onEach {
                when (it) {
                    is ApiResult.Data, null -> doNothing
                    is ApiResult.Forbidden -> logOutWithError()
                    is ApiResult.ApiError, is ApiResult.UnhandledError ->
                        showSnackbar(unhandledErrorSnackbar)
                }
            }
            .takeWhile { it is ApiResult.Data? }
            .map { it?.getOrNull()?.fileName }
            .toList()

        if (stepsCoversUrls.size < steps.size) {
            onFailure(errorSnackbar = unhandledErrorSnackbar)
            return
        }

        val recipeCoverRes = nullable {
            val data = (state().recipeParamsUiState.cover as? ImageContainer.Bytes?)?.value.bind()
            uploadCover(cover = data)
        }

        when (recipeCoverRes) {
            is ApiResult.Data -> then(recipeCoverRes.value.fileName, stepsCoversUrls)

            is ApiResult.Forbidden -> logOutWithError()

            is ApiResult.ApiError, is ApiResult.UnhandledError ->
                onFailure(errorSnackbar = unhandledErrorSnackbar)

            null -> then(null, stepsCoversUrls)
        }
    }

    private suspend inline fun uploadCover(cover: ByteArray) = acquireApiResult(
        result = withContext(AppDispatchers.Data) { recipeRepository.uploadCover(cover = cover) },
    )

    private suspend inline fun onSuccess(successSnackbar: SnackbarMessage) {
        showSnackbar(successSnackbar)
        publish(Label.Back)
    }

    private suspend inline fun onFailure(errorSnackbar: SnackbarMessage) {
        dispatch(Msg.UpdateUiState(UiState.Success))
        showSnackbar(errorSnackbar)
    }

    // -------------------- API results handling --------------------

    private suspend inline fun <T> handleRecipeApiResult(
        result: ApiResultWithCode<T>,
        unhandledErrorSnackbar: SnackbarMessage,
        onSuccess: (T) -> Unit,
    ) = handleApiResult(
        result = result,
        onUnhandledError = { showSnackbar(unhandledErrorSnackbar) },
        onErrorStatusCode = { status ->
            when {
                status.isForbidden -> logOutWithError()
                else -> onFailure(errorSnackbar = unhandledErrorSnackbar)
            }
        },
        onSuccess = onSuccess,
    )

    private suspend inline fun showSnackbar(snackbarMessage: SnackbarMessage) =
        globalEventRepository.sendSnackbar(snackbarMessage)

    private suspend inline fun logOutWithError() =
        globalEventRepository.sendLogOut(Reason.ERROR)
}
