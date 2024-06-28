package com.paranid5.cooking_corner.featrue.auth.presentation

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
data class PasswordHandling(
    val isPasswordVisible: Boolean,
    val isPasswordInvalid: Boolean,
    val onPasswordVisibilityChanged: () -> Unit,
)

@Composable
internal fun AuthEditText(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    passwordHandling: PasswordHandling? = null,
) {
    val textColor = textColor(
        isPassword = passwordHandling != null,
        isPasswordInvalid = passwordHandling?.isPasswordInvalid ?: false,
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = AppTheme.typography.h.h3,
        singleLine = true,
        visualTransformation = VisualTransformation(
            isPassword = passwordHandling != null,
            isPasswordVisible = passwordHandling?.isPasswordVisible ?: false,
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = textColor,
            disabledTextColor = textColor,
            backgroundColor = Color.Transparent,
            cursorColor = AppTheme.colors.text.tertiriary,
            focusedIndicatorColor = AppTheme.colors.text.tertiriary,
            disabledIndicatorColor = AppTheme.colors.text.tertiriary,
            unfocusedIndicatorColor = AppTheme.colors.text.tertiriary,
        ),
        placeholder = { AuthPlaceholder(text = placeholder) },
        trailingIcon = {
            passwordHandling?.onPasswordVisibilityChanged?.let {
                PasswordIcon(
                    isVisible = passwordHandling.isPasswordVisible,
                    onPasswordVisibilityChanged = it,
                )
            }
        }
    )
}

@Composable
private fun AuthPlaceholder(text: String, modifier: Modifier = Modifier) =
    Text(
        text = text,
        modifier = modifier,
        color = AppTheme.colors.text.tertiriary,
        style = AppTheme.typography.h.h3,
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

@Composable
private fun textColor(isPassword: Boolean, isPasswordInvalid: Boolean) = when {
    isPassword && isPasswordInvalid -> AppTheme.colors.error
    else -> AppTheme.colors.text.tertiriary
}

private fun VisualTransformation(isPassword: Boolean, isPasswordVisible: Boolean) = when {
    isPassword && isPasswordVisible.not() -> PasswordVisualTransformation(mask = PASSWORD_MASK)
    else -> VisualTransformation.None
}