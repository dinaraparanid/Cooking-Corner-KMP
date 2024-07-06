package com.paranid5.cooking_corner.feature.main.splash.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.main_splash_begin
import com.paranid5.cooking_corner.core.resources.main_splash_screen
import com.paranid5.cooking_corner.core.resources.main_splash_text
import com.paranid5.cooking_corner.core.resources.main_splash_title
import com.paranid5.cooking_corner.feature.main.splash.component.MainSplashScreenComponent
import com.paranid5.cooking_corner.feature.main.splash.component.MainSplashScreenUiIntent
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.RippleButton
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val IMAGE_SIZE = 253.dp
private val IMAGE_START_PADDING = 94.dp
private val IMAGE_END_PADDING = 43.dp
private val IMAGE_BUTTON_PADDING = 81.dp
private val BUTTON_BOTTOM_PADDING = 128.dp

@Composable
fun MainSplashScreenUi(
    component: MainSplashScreenComponent,
    modifier: Modifier = Modifier,
) {
    val appPadding = AppTheme.dimensions.padding

    ConstraintLayout(modifier = modifier, animateChanges = true) {
        val (
            title,
            text,
            image,
            beginButton,
        ) = createRefs()

        MainSplashScreenTitle(
            Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = appPadding.large)
                start.linkTo(parent.start, margin = appPadding.extraLarge)
                end.linkTo(parent.end, margin = appPadding.extraLarge)
            }
        )

        MainSplashScreenText(
            Modifier.constrainAs(text) {
                top.linkTo(title.bottom, margin = appPadding.extraMedium)
                start.linkTo(parent.start, margin = appPadding.extraLarge)
                end.linkTo(parent.end, margin = appPadding.extraLarge)
            }
        )

        MainSplashScreenImage(
            Modifier.size(IMAGE_SIZE).constrainAs(image) {
                top.linkTo(text.bottom, margin = appPadding.extraBig)
                start.linkTo(parent.start, margin = IMAGE_START_PADDING)
                end.linkTo(parent.end, margin = IMAGE_END_PADDING)
            }
        )

        MainSplashScreenBeginButton(
            onClick = { component.onUiIntent(MainSplashScreenUiIntent.CloseSplashScreen) },
            modifier = Modifier.constrainAs(beginButton) {
                top.linkTo(image.bottom, margin = IMAGE_BUTTON_PADDING)
                bottom.linkTo(parent.bottom, margin = BUTTON_BOTTOM_PADDING)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            },
        )
    }
}

@Composable
private fun MainSplashScreenTitle(modifier: Modifier = Modifier) = Text(
    text = stringResource(Res.string.main_splash_title),
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h1,
    textAlign = TextAlign.Center,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    fontWeight = FontWeight.Bold,
    modifier = modifier,
)

@Composable
private fun MainSplashScreenText(modifier: Modifier = Modifier) = Text(
    text = stringResource(Res.string.main_splash_text),
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.regular,
    textAlign = TextAlign.Center,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)

@Composable
private fun MainSplashScreenImage(modifier: Modifier = Modifier) = Image(
    painter = painterResource(Res.drawable.main_splash_screen),
    contentDescription = null,
    modifier = modifier,
)

@Composable
private fun MainSplashScreenBeginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) = RippleButton(
    onClick = onClick,
    shape = RoundedCornerShape(AppTheme.dimensions.corners.extraSmall),
    border = BorderStroke(
        width = AppTheme.dimensions.borders.extraSmall,
        color = AppTheme.colors.button.primary,
    ),
    modifier = modifier.simpleShadow(
        radius = AppTheme.dimensions.corners.extraSmall,
    ),
    colors = ButtonDefaults.buttonColors(
        containerColor = AppTheme.colors.button.secondary,
        disabledContainerColor = AppTheme.colors.button.secondary,
    ),
) {
    Text(
        text = stringResource(Res.string.main_splash_begin),
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.body,
        fontFamily = AppTheme.typography.RalewayFontFamily,
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(vertical = AppTheme.dimensions.padding.medium),
    )
}