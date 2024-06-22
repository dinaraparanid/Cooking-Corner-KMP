package com.paranid5.cooking_corner.feature.main.content.presentation.navbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.content.component.MainContentChild
import com.paranid5.cooking_corner.feature.main.content.component.MainContentUiIntent

@Composable
internal actual fun NavBar(
    currentScreen: MainContentChild,
    onUiIntent: (MainContentUiIntent) -> Unit,
    modifier: Modifier,
) = NavBarMobile(
    currentScreen = currentScreen,
    onUiIntent = onUiIntent,
    modifier = modifier,
)