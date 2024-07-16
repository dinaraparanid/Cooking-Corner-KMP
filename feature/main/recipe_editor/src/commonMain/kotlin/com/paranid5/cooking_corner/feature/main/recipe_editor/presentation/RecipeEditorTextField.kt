package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.ui.foundation.AppAnimatedOutlinedEditText

@Composable
internal fun RecipeEditorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
) = AppAnimatedOutlinedEditText(
    value = value,
    onValueChange = onValueChange,
    placeholder = placeholder,
    isError = isError,
    errorMessage = errorMessage,
    modifier = modifier,
    shape = ParamShape,
)
