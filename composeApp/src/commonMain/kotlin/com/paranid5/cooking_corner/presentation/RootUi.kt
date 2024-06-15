package com.paranid5.cooking_corner.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.component.root.RootChild
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.component.root.RootConfig
import com.paranid5.cooking_corner.feature.splash.SplashScreenUi
import com.paranid5.cooking_corner.ui.theme.AppTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RootUi(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier,
) = AppTheme {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context).build()
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
    ) { screenPadding ->
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .padding(screenPadding)
                .background(brush = AppTheme.colors.backgroundGradient),
        ) {
            RootContent(
                childStackState = rootComponent.stack.collectAsState(),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun RootContent(
    childStackState: State<ChildStack<RootConfig, RootChild>>,
    modifier: Modifier = Modifier,
) {
    val childStack by childStackState

    Children(
        modifier = modifier,
        stack = childStack,
    ) { child ->
        when (val instance = child.instance) {
            is RootChild.SplashScreen -> SplashScreenUi(
                component = instance.component,
                modifier = Modifier.fillMaxSize(),
            )

            is RootChild.Auth -> Text("TODO: Auth screen")

            is RootChild.Home -> Text("TODO: Home screen")
        }
    }
}
