package com.paranid5.cooking_corner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.retainedComponent
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.presentation.RootUi
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class MainActivity : ComponentActivity(), DIAware {
    override val di: DI by closestDI()
    private val rootComponentFactory: RootComponent.Factory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val rootComponent = retainedComponent {
            rootComponentFactory.create(componentContext = it)
        }

        setContent {
            RootUi(
                rootComponent = rootComponent,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
