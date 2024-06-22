package com.paranid5.cooking_corner.feature.main.content.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.feature.main.content.component.MainContentChild
import com.paranid5.cooking_corner.feature.main.content.component.MainContentConfig
import com.paranid5.cooking_corner.feature.main.content.component.MainContentUiIntent

@Composable
internal actual fun MainContentUiImpl(
    currentScreen: MainContentChild,
    childStack: State<ChildStack<MainContentConfig, MainContentChild>>,
    onUiIntent: (MainContentUiIntent) -> Unit,
    modifier: Modifier
) = MainContentUiImplMobile(
    currentScreen = currentScreen,
    childStack = childStack,
    onUiIntent = onUiIntent,
    modifier = modifier,
)