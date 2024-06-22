package com.paranid5.cooking_corner.feature.main.root.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.paranid5.cooking_corner.feature.main.content.presentation.MainContentUi
import com.paranid5.cooking_corner.feature.main.root.component.MainRootChild
import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponent
import com.paranid5.cooking_corner.feature.main.splash.presentation.MainSplashScreenUi

@Composable
fun MainRootUi(
    component: MainRootComponent,
    modifier: Modifier = Modifier,
) {
    val childStack by component.stack.collectAsState()

    Children(
        stack = childStack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) { child ->
        when (val instance = child.instance) {
            is MainRootChild.Content -> MainContentUi(
                component = instance.component,
                modifier = Modifier.fillMaxSize(),
            )

            is MainRootChild.SplashScreen -> MainSplashScreenUi(
                component = instance.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}