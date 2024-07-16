package com.paranid5.cooking_corner.feature.main.profile_editor.presentation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.ui.foundation.AppAnimatedOutlinedEditText
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
internal fun ProfileEditorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    suffix: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) = AppAnimatedOutlinedEditText(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    placeholder = placeholder,
    isError = isError,
    errorMessage = errorMessage,
    suffix = suffix,
    keyboardOptions = keyboardOptions,
    shape = RoundedCornerShape(AppTheme.dimensions.corners.extraSmall),
)
