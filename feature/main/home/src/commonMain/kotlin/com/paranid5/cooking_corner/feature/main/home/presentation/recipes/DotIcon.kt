package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_dot
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun DotIcon(modifier: Modifier = Modifier) = Image(
    imageVector = vectorResource(Res.drawable.ic_dot),
    contentDescription = null,
    modifier = modifier,
)