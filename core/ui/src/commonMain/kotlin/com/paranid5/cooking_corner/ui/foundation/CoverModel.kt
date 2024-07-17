package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.runtime.Composable
import coil3.PlatformContext
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import coil3.size.Scale

private const val DEFAULT_ANIMATION_DURATION = 400

fun coverModel(
    data: Any?,
    context: PlatformContext,
    animationMillis: Int = DEFAULT_ANIMATION_DURATION,
): ImageRequest = ImageRequest.Builder(context)
    .data(data)
    .diskCachePolicy(CachePolicy.WRITE_ONLY)
    .memoryCachePolicy(CachePolicy.DISABLED)
    .precision(Precision.EXACT)
    .scale(Scale.FILL)
    .crossfade(animationMillis)
    .build()

@Composable
fun coverModel(
    data: Any?,
    animationMillis: Int = DEFAULT_ANIMATION_DURATION,
) = coverModel(
    data = data,
    context = LocalPlatformContext.current,
    animationMillis = animationMillis,
)