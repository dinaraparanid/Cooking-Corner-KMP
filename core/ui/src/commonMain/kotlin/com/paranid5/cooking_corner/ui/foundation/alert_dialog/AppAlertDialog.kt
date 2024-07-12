package com.paranid5.cooking_corner.ui.foundation.alert_dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.cancel
import com.paranid5.cooking_corner.core.resources.ok
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppAlertDialog(
    text: String,
    callbacks: AppAlertDialogCallbacks,
    modifier: Modifier = Modifier,
    confirmButtonModifier: Modifier = Modifier,
    cancelButtonModifier: Modifier = Modifier,
    title: String? = null,
    confirmButtonTitle: String = stringResource(Res.string.ok),
    cancelButtonTitle: String? = stringResource(Res.string.cancel),
    contentModifiers: AppAlertDialogTitleContentModifiers =
        AppAlertDialogTitleContentModifiers.default(hasTitle = title != null),
    properties: AppAlertDialogProperties = AppAlertDialogProperties.create(),
) {
    val textStyle = AppTheme.typography.run { if (title != null) regular else h.h3 }
    val textColor = AppTheme.colors.text.run { if (title != null) secondary else primary }
    val textFontWeight = if (title != null) FontWeight.Normal else FontWeight.Bold

    AppAlertDialog(
        confirmButtonTitle = confirmButtonTitle,
        callbacks = callbacks,
        modifier = modifier,
        confirmButtonModifier = confirmButtonModifier,
        cancelButtonModifier = cancelButtonModifier,
        cancelButtonTitle = cancelButtonTitle,
        title = title?.let {
            {
                AppAlertDialogTitle(
                    title = title,
                    modifier = contentModifiers.titleModifier
                )
            }
        },
        properties = properties,
    ) {
        AppAlertDialogText(
            text = text,
            textStyle = textStyle,
            color = textColor,
            fontWeight = textFontWeight,
            modifier = contentModifiers.contentModifier,
        )
    }
}

@Composable
fun AppAlertDialog(
    confirmButtonTitle: String,
    callbacks: AppAlertDialogCallbacks,
    modifier: Modifier = Modifier,
    confirmButtonModifier: Modifier = Modifier,
    cancelButtonModifier: Modifier = Modifier,
    cancelButtonTitle: String? = stringResource(Res.string.cancel),
    title: @Composable (ColumnScope.() -> Unit)? = null,
    properties: AppAlertDialogProperties = AppAlertDialogProperties.create(),
    content: @Composable (ColumnScope.() -> Unit)? = null,
) = Dialog(onDismissRequest = callbacks.onDismissRequest) {
    Surface(
        modifier = modifier,
        shape = properties.shape,
        color = properties.colors.containerColor,
        contentColor = properties.colors.contentColor,
    ) {
        Column(Modifier.fillMaxWidth()) {
            if (title != null) title()
            if (content != null) content()

            Box(Modifier.align(Alignment.End)) {
                Row(Modifier.padding(AppTheme.dimensions.padding.extraBig)) {
                    cancelButtonTitle?.let {
                        AppAlertDialogButton(
                            modifier = cancelButtonModifier,
                            onClick = callbacks.onCancelButtonClick ?: callbacks.onDismissRequest,
                            title = cancelButtonTitle,
                            textColor = properties.colors.confirmButtonTextColor,
                        )
                    }

                    AppAlertDialogButton(
                        modifier = confirmButtonModifier,
                        onClick = callbacks.onConfirmButtonClick,
                        title = confirmButtonTitle,
                        textColor = properties.colors.confirmButtonTextColor,
                    )
                }
            }
        }
    }
}

@Composable
fun AppAlertDialogTitle(title: String, modifier: Modifier = Modifier) = AppMainText(
    text = title,
    style = AppTheme.typography.h.h3,
    fontWeight = FontWeight.Bold,
    modifier = modifier,
)

@Composable
private fun AppAlertDialogText(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.text.secondary,
    fontWeight: FontWeight? = null,
) = AppMainText(
    text = text,
    style = textStyle,
    color = color,
    fontWeight = fontWeight,
    modifier = modifier,
)

@Composable
private fun AppAlertDialogButton(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    textColor: Color = AppTheme.colors.text.primary,
) = AppRippleButton(
    modifier = modifier,
    onClick = onClick,
    content = {
        AppMainText(
            text = title,
            style = AppTheme.typography.regular,
            color = textColor,
        )
    }
)
