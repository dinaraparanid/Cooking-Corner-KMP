package com.paranid5.cooking_corner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.paranid5.cooking_corner.component.root.RootComponent
import com.paranid5.cooking_corner.presentation.RootUi

fun MainViewController(rootComponent: RootComponent) = ComposeUIViewController {
    RootUi(
        rootComponent = rootComponent,
        modifier = Modifier.fillMaxSize(),
    )
}