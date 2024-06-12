package com.paranid5.cooking_corner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.core.utils.setMainThreadId
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.di.initKodein
import com.paranid5.cooking_corner.presentation.App
import org.kodein.di.instance
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeAndWait { setMainThreadId(Thread.currentThread().id) }

    val kodein = initKodein()
    val rootComponentFactory: RootComponent.Factory by kodein.instance()
    val rootComponent = runOnUiThread {
        rootComponentFactory.create(
            DefaultComponentContext(
                lifecycle = LifecycleRegistry(),
            )
        )
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "CookingCorner",
        ) {
            App(modifier = Modifier.fillMaxSize())
        }
    }
}