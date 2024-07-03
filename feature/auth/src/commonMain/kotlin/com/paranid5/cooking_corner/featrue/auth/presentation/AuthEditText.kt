package com.paranid5.cooking_corner.featrue.auth.presentation

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_password_hide
import com.paranid5.cooking_corner.core.resources.ic_password_show
import com.paranid5.cooking_corner.ui.foundation.AppIconButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

private const val PASSWORD_MASK = '*'

@Immutable
data class PasswordVisibilityHandling(
    val isPasswordVisible: Boolean,
    val onPasswordVisibilityChanged: () -> Unit,
)

@Composable
internal fun AuthEditText(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    passwordVisibilityHandling: PasswordVisibilityHandling? = null,
    isError: Boolean = false,
    errorText: String? = null,
) = TextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    textStyle = AppTheme.typography.h.h3,
    singleLine = true,
    isError = isError,
    visualTransformation = VisualTransformation(
        isPassword = passwordVisibilityHandling != null,
        isPasswordVisible = passwordVisibilityHandling?.isPasswordVisible ?: false,
    ),
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
        focusedIndicatorColor = AppTheme.colors.text.tertiriary,
        disabledIndicatorColor = AppTheme.colors.text.tertiriary,
        unfocusedIndicatorColor = AppTheme.colors.text.tertiriary,
    ),
    placeholder = { AuthPlaceholder(text = placeholder) },
    supportingText = { if (isError) errorText?.let { AuthErrorText(text = it) } },
    trailingIcon = {
        passwordVisibilityHandling?.onPasswordVisibilityChanged?.let {
            PasswordIcon(
                isVisible = passwordVisibilityHandling.isPasswordVisible,
                onPasswordVisibilityChanged = it,
            )
        }
    },
)

@Composable
private fun AuthPlaceholder(text: String, modifier: Modifier = Modifier) = Text(
    text = text,
    modifier = modifier,
    color = AppTheme.colors.text.tertiriary,
    style = AppTheme.typography.h.h3,
    fontFamily = AppTheme.typography.InterFontFamily,
)

@Composable
private fun AuthErrorText(text: String, modifier: Modifier = Modifier) = Text(
    text = text,
    modifier = modifier,
    color = AppTheme.colors.error,
    style = AppTheme.typography.h.h3,
    fontFamily = AppTheme.typography.InterFontFamily,
)

@Composable
private fun PasswordIcon(
    isVisible: Boolean,
    onPasswordVisibilityChanged: () -> Unit,
    modifier: Modifier = Modifier,
) = AppIconButton(
    onClick = onPasswordVisibilityChanged,
    modifier = modifier,
    icon = vectorResource(
        when {
            isVisible -> Res.drawable.ic_password_hide
            else -> Res.drawable.ic_password_show
        }
    ),
)

private fun VisualTransformation(isPassword: Boolean, isPasswordVisible: Boolean) = when {
    isPassword && isPasswordVisible.not() -> PasswordVisualTransformation(mask = PASSWORD_MASK)
    else -> VisualTransformation.None
}