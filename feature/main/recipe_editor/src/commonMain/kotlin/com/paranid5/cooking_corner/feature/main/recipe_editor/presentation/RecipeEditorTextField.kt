package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedEditText

@Composable
internal fun RecipeEditorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) = AppOutlinedEditText(
    value = value,
    onValueChange = onValueChange,
    placeholder = placeholder,
    modifier = modifier,
    shape = ParamShape,
)