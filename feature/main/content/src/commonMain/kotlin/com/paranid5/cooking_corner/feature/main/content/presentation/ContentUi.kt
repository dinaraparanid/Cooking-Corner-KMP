package com.paranid5.cooking_corner.feature.main.content.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.feature.main.content.component.MainContentChild
import com.paranid5.cooking_corner.feature.main.content.component.MainContentConfig
import com.paranid5.cooking_corner.feature.main.content.component.MainContentUiIntent

@Composable
internal fun ContentUi(
    childStack: State<ChildStack<MainContentConfig, MainContentChild>>,
    modifier: Modifier = Modifier,
) {
    val stack by childStack

    Children(
        stack = stack,
        animation = stackAnimation(fade()),
        modifier = modifier,
    ) {
        when (val child = it.instance) {
            is MainContentChild.Home -> Text("TODO: Home", Modifier.fillMaxSize())
            is MainContentChild.Profile -> Text("TODO: Profile", Modifier.fillMaxSize())
            is MainContentChild.Search -> Text("TODO: Search", Modifier.fillMaxSize())
        }
    }
}