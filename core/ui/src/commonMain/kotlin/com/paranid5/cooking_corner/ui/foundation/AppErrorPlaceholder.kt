package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.placeholder_recipe
import org.jetbrains.compose.resources.vectorResource

@Composable
fun AppErrorPlaceholder(modifier: Modifier = Modifier) =
    Box(modifier = modifier) {
        Image(
            imageVector = vectorResource(Res.drawable.placeholder_recipe),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
        )
    }