package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun AppOutlinedEditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    singleLine: Boolean = true,
    shape: Shape = RoundedCornerShape(AppTheme.dimensions.corners.extraSmall),
) = TextField(
    value = value,
    onValueChange = onValueChange,
    shape = shape,
    textStyle = AppTheme.typography.body.copy(
        fontFamily = AppTheme.typography.RalewayFontFamily,
    ),
    singleLine = singleLine,
    colors = TextFieldDefaults.colors(
        focusedTextColor = AppTheme.colors.text.tertiriary,
        unfocusedTextColor = AppTheme.colors.text.tertiriary,
        disabledTextColor = AppTheme.colors.text.tertiriary,
        errorTextColor = AppTheme.colors.error,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        cursorColor = AppTheme.colors.text.tertiriary,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    ),
    placeholder = placeholder?.let { { ExtraText(text = it) } },
    modifier = modifier.border(
        width = AppTheme.dimensions.borders.minimum,
        color = AppTheme.colors.button.primary,
        shape = shape,
    ),
)

@Composable
fun AppAnimatedOutlinedEditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    singleLine: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    suffix: String? = null,
    shape: Shape = RoundedCornerShape(AppTheme.dimensions.corners.extraSmall),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) = OutlinedTextField(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    shape = shape,
    textStyle = AppTheme.typography.body.copy(
        fontFamily = AppTheme.typography.RalewayFontFamily,
    ),
    singleLine = singleLine,
    isError = isError,
    keyboardOptions = keyboardOptions,
    colors = TextFieldDefaults.colors(
        focusedTextColor = AppTheme.colors.text.tertiriary,
        unfocusedTextColor = AppTheme.colors.text.tertiriary,
        disabledTextColor = AppTheme.colors.text.tertiriary,
        errorTextColor = AppTheme.colors.error,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        cursorColor = AppTheme.colors.text.tertiriary,
        focusedIndicatorColor = AppTheme.colors.button.primary,
        disabledIndicatorColor = AppTheme.colors.button.primary,
        unfocusedIndicatorColor = AppTheme.colors.button.primary,
    ),
    label = placeholder?.let { { ExtraText(text = it) } },
    suffix = suffix?.let { { ExtraText(text = it) } },
    supportingText = errorMessage.takeIf { isError }?.let { { Error(text = it) } },
)

@Composable
private fun ExtraText(text: String, modifier: Modifier = Modifier) = AppMainText(
    text = text,
    modifier = modifier,
    color = AppTheme.colors.text.tertiriary,
    style = AppTheme.typography.body,
)

@Composable
private fun Error(text: String, modifier: Modifier = Modifier) = AppMainText(
    text = text,
    modifier = modifier,
    color = AppTheme.colors.error,
    style = AppTheme.typography.regular,
)
