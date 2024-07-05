package com.paranid5.cooking_corner.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
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
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.component.root.RootChild
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.featrue.auth.presentation.AuthUi
import com.paranid5.cooking_corner.feature.main.root.presentation.MainRootUi
import com.paranid5.cooking_corner.feature.splash.presentation.SplashScreenUi
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
        containerColor = Color.Transparent,
        modifier = modifier
            .background(AppTheme.colors.background.primary)
            .windowInsetsPadding(WindowInsets.safeDrawing),
    ) { screenPadding ->
        Surface(
            color = Color.Transparent,
            modifier = Modifier
                .fillMaxSize()
                .padding(screenPadding)
                .background(color = AppTheme.colors.background.primary),
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
    childStackState: State<ChildStack<*, RootChild>>,
    modifier: Modifier = Modifier,
) {
    val childStack by childStackState

    Children(
        modifier = modifier,
        stack = childStack,
        animation = stackAnimation(fade())
    ) { child ->
        when (val instance = child.instance) {
            is RootChild.SplashScreen -> SplashScreenUi(
                modifier = Modifier.fillMaxSize(),
            )

            is RootChild.Auth -> AuthUi(
                component = instance.component,
                modifier = Modifier.fillMaxSize(),
            )

            is RootChild.Main -> MainRootUi(
                component = instance.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
