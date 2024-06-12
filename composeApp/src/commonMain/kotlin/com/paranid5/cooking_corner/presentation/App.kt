package com.paranid5.cooking_corner.presentation

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.app_name
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier
) {
    AppTheme {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context).build()
        }

        Surface(
            modifier = modifier.background(brush = AppTheme.colors.backgroundGradient),
            color = Color.Transparent
        ) {
            Text(stringResource(Res.string.app_name))
        }
    }
}