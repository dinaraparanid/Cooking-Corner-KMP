package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.retry
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.applyIf
import com.paranid5.cooking_corner.ui.utils.shimmerEffect
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppLoadingBox(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onErrorButtonClick: (() -> Unit)? = null,
    errorBackgroundColor: Color = AppTheme.colors.background.primaryDarker,
    errorText: String = stringResource(Res.string.something_went_wrong),
    errorButtonText: String = stringResource(Res.string.retry),
    contentAlignment: Alignment = Alignment.Center,
    propagateMinConstraints: Boolean = false,
    content: (@Composable BoxScope.() -> Unit)? = null,
) = Box(
    contentAlignment = contentAlignment,
    propagateMinConstraints = propagateMinConstraints,
    modifier = modifier.applyIf(isLoading) { shimmerEffect() },
) {
    if (isLoading.not()) {
        when {
            isError -> AppLoadingBoxError(
                onErrorButtonClick = onErrorButtonClick,
                errorText = errorText,
                errorButtonText = errorButtonText,
                modifier = Modifier
                    .fillMaxSize()
                    .background(errorBackgroundColor),
            )

            else -> if (content != null) content()
        }
    }
}

@Composable
fun AppLoadingBoxError(
    modifier: Modifier = Modifier,
    errorText: String = stringResource(Res.string.something_went_wrong),
    errorButtonText: String = stringResource(Res.string.retry),
    onErrorButtonClick: (() -> Unit)? = null,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = errorText,
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.caption,
        fontFamily = AppTheme.typography.RalewayFontFamily,
    )
    onErrorButtonClick?.let {
        Spacer(Modifier.height(AppTheme.dimensions.padding.extraSmall))

        AppRippleButton(
            onClick = onErrorButtonClick,
            rippleColor = AppTheme.colors.background.alternative,
            shape = RoundedCornerShape(AppTheme.dimensions.corners.small),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.background.alternative,
                disabledContainerColor = AppTheme.colors.background.alternative,
            )
        ) {
            Text(
                text = errorButtonText,
                color = AppTheme.colors.text.selected,
                style = AppTheme.typography.caption,
                fontFamily = AppTheme.typography.RalewayFontFamily,
            )
        }
    }
}