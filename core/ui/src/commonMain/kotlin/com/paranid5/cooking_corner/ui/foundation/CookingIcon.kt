package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.cooking_logo
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CookingIcon(modifier: Modifier = Modifier) = Image(
    imageVector = vectorResource(Res.drawable.cooking_logo),
    contentDescription = null,
    modifier = modifier,
)