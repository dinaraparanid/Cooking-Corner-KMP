package com.paranid5.cooking_corner.presentation.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.cancel
import com.paranid5.cooking_corner.core.resources.retry
import com.paranid5.cooking_corner.domain.snackbar.SnackbarGravity
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.applyIfNotNull
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AppSnackbar(
    message: String,
    snackbarType: SnackbarType,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    gravity: SnackbarGravity = SnackbarGravity.Bottom(),
    onRetry: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    onAction: (() -> Unit)?,
) = Box(modifier = modifier) {
    val snackbarModifier by rememberSnackbarModifier(
        gravity = gravity,
        padding = PaddingValues(AppTheme.dimensions.padding.extraMedium),
    )

    Snackbar(
        modifier = snackbarModifier,
        containerColor = snackbarType.containerColor,
        contentColor = snackbarType.contentColor,
        action = showActionLabel(
            actionLabel = actionLabel,
            onAction = onAction,
            modifier = Modifier.padding(end = AppTheme.dimensions.padding.extraMedium),
        ),
        dismissAction = showDismissIcon(
            snackbarType = snackbarType,
            onDismiss = onDismiss,
            modifier = Modifier.padding(end = AppTheme.dimensions.padding.extraMedium),
        )
    ) {
        AppSnackbarContent(
            message = message,
            snackbarType = snackbarType,
            onRetry = onRetry,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun AppSnackbarContent(
    message: String,
    snackbarType: SnackbarType,
    onRetry: (() -> Unit)?,
    modifier: Modifier = Modifier,
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
) {
    AppMainText(
        modifier = Modifier.weight(1F),
        text = message,
        textAlign = TextAlign.Start,
        style = AppTheme.typography.regular,
        color = snackbarType.contentColor
    )

    Spacer(Modifier.width(AppTheme.dimensions.padding.extraSmall))

    if (onRetry != null)
        AppMainText(
            text = stringResource(Res.string.retry),
            style = AppTheme.typography.regular,
            modifier = Modifier.clickableWithRipple(onClick = onRetry),
        )
}

@Composable
private fun showActionLabel(
    actionLabel: String?,
    modifier: Modifier = Modifier,
    onAction: (() -> Unit)?,
): (@Composable () -> Unit)? = actionLabel?.let {
    {
        AppMainText(
            text = actionLabel,
            textAlign = TextAlign.Center,
            style = AppTheme.typography.regular,
            modifier = modifier.applyIfNotNull(onAction) { clickableWithRipple(onClick = it) },
        )
    }
}

@Composable
private fun showDismissIcon(
    snackbarType: SnackbarType,
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)?,
): (@Composable () -> Unit)? = onDismiss?.let {
    {
        Icon(
            imageVector = Icons.Default.Close,
            tint = snackbarType.contentColor,
            contentDescription = stringResource(Res.string.cancel),
            modifier = modifier.clickableWithRipple(onClick = onDismiss),
        )
    }
}

@Composable
private fun BoxScope.rememberSnackbarModifier(
    gravity: SnackbarGravity,
    padding: PaddingValues = PaddingValues(),
): State<Modifier> {
    val layoutDirection = LocalLayoutDirection.current

    return remember(gravity, padding) {
        derivedStateOf {
            when (gravity) {
                is SnackbarGravity.Top -> Modifier
                    .align(Alignment.TopCenter)
                    .padding(
                        top = gravity.topPadding,
                        bottom = padding.calculateBottomPadding(),
                        start = padding.calculateStartPadding(layoutDirection),
                        end = padding.calculateEndPadding(layoutDirection),
                    )

                is SnackbarGravity.Bottom -> Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = gravity.bottomPadding,
                        start = padding.calculateStartPadding(layoutDirection),
                        end = padding.calculateEndPadding(layoutDirection),
                    )
            }
        }
    }
}

private val SnackbarType.containerColor: Color
    @Composable
    get() = when (this) {
        SnackbarType.NEUTRAL -> AppTheme.colors.snackbar.background.neutral
        SnackbarType.POSITIVE -> AppTheme.colors.snackbar.background.positive
        SnackbarType.NEGATIVE -> AppTheme.colors.snackbar.background.negative
    }

private val SnackbarType.contentColor: Color
    @Composable
    get() = when (this) {
        SnackbarType.NEUTRAL -> AppTheme.colors.snackbar.text.neutral
        SnackbarType.POSITIVE -> AppTheme.colors.snackbar.text.positive
        SnackbarType.NEGATIVE -> AppTheme.colors.snackbar.text.negative
    }