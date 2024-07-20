package com.paranid5.cooking_corner.feature.main.recipe.component

import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage

interface RecipeUiIntent {
    data object Back : RecipeUiIntent

    data object Refresh : RecipeUiIntent

    data class ChangeKebabMenuVisibility(val isVisible: Boolean) : RecipeUiIntent

    data class Publish(
        val errorSnackbar: SnackbarMessage,
        val successSnackbar: SnackbarMessage,
    ) : RecipeUiIntent

    data class Delete(
        val unhandledErrorSnackbar: SnackbarMessage,
        val recipeCannotBeDeletedSnackbar: SnackbarMessage,
        val successSnackbar: SnackbarMessage,
    ) : RecipeUiIntent

    data object Edit : RecipeUiIntent

    data class Rate(
        val rating: Int,
        val errorSnackbar: SnackbarMessage,
    ) : RecipeUiIntent
}