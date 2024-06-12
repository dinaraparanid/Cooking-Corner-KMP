package com.paranid5.cooking_corner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.paranid5.cooking_corner.presentation.App

fun MainViewController() = ComposeUIViewController {
    App(modifier = Modifier.fillMaxSize())
}