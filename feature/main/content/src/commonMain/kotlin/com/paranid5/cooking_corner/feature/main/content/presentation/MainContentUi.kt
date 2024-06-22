package com.paranid5.cooking_corner.feature.main.content.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.feature.main.content.component.MainContentChild
import com.paranid5.cooking_corner.feature.main.content.component.MainContentComponent
import com.paranid5.cooking_corner.feature.main.content.component.MainContentConfig
import com.paranid5.cooking_corner.feature.main.content.component.MainContentUiIntent
import com.paranid5.cooking_corner.feature.main.content.presentation.navbar.NavBar
import kotlinx.coroutines.flow.map

@Composable
fun MainContentUi(
    component: MainContentComponent,
    modifier: Modifier = Modifier,
) {
    val currentScreen by rememberCurrentScreen(component)
    val childStack = component.stack.collectAsState()
    val onUiIntent = component::onUiIntent

    MainContentUiImpl(
        currentScreen = currentScreen,
        childStack = childStack,
        onUiIntent = onUiIntent,
        modifier = modifier,
    )
}

@Composable
internal expect fun MainContentUiImpl(
    currentScreen: MainContentChild,
    childStack: State<ChildStack<MainContentConfig, MainContentChild>>,
    onUiIntent: (MainContentUiIntent) -> Unit,
    modifier: Modifier = Modifier,
)

@Composable
internal fun MainContentUiImplMobile(
    currentScreen: MainContentChild,
    childStack: State<ChildStack<MainContentConfig, MainContentChild>>,
    onUiIntent: (MainContentUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val direction = LocalLayoutDirection.current

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavBar(
                currentScreen = currentScreen,
                onUiIntent = onUiIntent,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
        content = { paddingValues ->
            ContentUi(
                childStack = childStack,
                modifier = Modifier.padding(
                    top = 0.dp,
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(direction),
                    end = paddingValues.calculateEndPadding(direction),
                ),
            )
        }
    )
}

@Composable
internal fun MainContentUiImplPC(
    currentScreen: MainContentChild,
    childStack: State<ChildStack<MainContentConfig, MainContentChild>>,
    onUiIntent: (MainContentUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val direction = LocalLayoutDirection.current

    Row(modifier) {
        NavBar(
            currentScreen = currentScreen,
            onUiIntent = onUiIntent,
            modifier = Modifier.fillMaxHeight()
        )

        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            content = { paddingValues ->
                ContentUi(
                    childStack = childStack,
                    modifier = Modifier.padding(
                        top = 0.dp,
                        bottom = paddingValues.calculateBottomPadding(),
                        start = paddingValues.calculateStartPadding(direction),
                        end = paddingValues.calculateEndPadding(direction),
                    ),
                )
            }
        )
    }
}

@Composable
private fun rememberCurrentScreen(component: MainContentComponent) =
    component.stack.map { it.active.instance }.collectAsState(MainContentChild.Search)