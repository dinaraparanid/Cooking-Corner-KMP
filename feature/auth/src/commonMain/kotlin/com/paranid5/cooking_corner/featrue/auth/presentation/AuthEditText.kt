package com.paranid5.cooking_corner.featrue.auth.presentation

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
internal fun AuthEditText(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
) = TextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    textStyle = AppTheme.typography.h.h3,
    singleLine = true,
    visualTransformation = visualTransformation,
    colors = TextFieldDefaults.textFieldColors(
        textColor = AppTheme.colors.text.tertiriary,
        disabledTextColor = AppTheme.colors.text.tertiriary,
        backgroundColor = Color.Transparent,
        cursorColor = AppTheme.colors.text.tertiriary,
        focusedIndicatorColor = AppTheme.colors.text.tertiriary,
        disabledIndicatorColor = AppTheme.colors.text.tertiriary,
        unfocusedIndicatorColor = AppTheme.colors.text.tertiriary,
    ),
    placeholder = { AuthPlaceholder(text = placeholder) },
)

@Composable
private fun AuthPlaceholder(text: String, modifier: Modifier = Modifier) =
    Text(
        text = text,
        modifier = modifier,
        color = AppTheme.colors.text.tertiriary,
        style = AppTheme.typography.h.h3,
    )