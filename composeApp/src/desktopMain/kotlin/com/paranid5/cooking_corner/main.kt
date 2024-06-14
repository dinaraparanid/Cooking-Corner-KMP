package com.paranid5.cooking_corner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.core.utils.setMainThreadId
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.app_name
import com.paranid5.cooking_corner.di.initKodein
import com.paranid5.cooking_corner.presentation.RootUi
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.instance
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeAndWait { setMainThreadId(Thread.currentThread().id) }

    val kodein = initKodein()
    val rootComponentFactory: RootComponent.Factory by kodein.instance()

    val lifecycle = LifecycleRegistry()
    val rootComponent = runOnUiThread {
        rootComponentFactory.create(DefaultComponentContext(lifecycle))
    }

    application {
        val windowState = rememberWindowState()
        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = stringResource(Res.string.app_name),
        ) {
            RootUi(
                rootComponent = rootComponent,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}