package com.paranid5.cooking_corner.feature.splash.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.ui.foundation.CookingCornerLabel
import com.paranid5.cooking_corner.ui.foundation.CookingIcon
import com.paranid5.cooking_corner.ui.theme.AppTheme

private val COOKING_ICON_WIDTH = 105.dp
private val COOKING_ICON_HEIGHT = 134.dp

@Composable
fun SplashScreenUi(modifier: Modifier = Modifier) =
    Box(modifier) {
        Column(Modifier.align(Alignment.Center)) {
            CookingIcon(
                Modifier
                    .width(COOKING_ICON_WIDTH)
                    .height(COOKING_ICON_HEIGHT)
                    .align(Alignment.CenterHorizontally),
            )

            Spacer(Modifier.height(AppTheme.dimensions.padding.extraSmall))

            CookingCornerLabel(Modifier.align(Alignment.CenterHorizontally))
        }
    }