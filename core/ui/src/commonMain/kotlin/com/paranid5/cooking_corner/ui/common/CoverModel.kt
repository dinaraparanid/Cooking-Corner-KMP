package com.paranid5.cooking_corner.ui.common

import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import coil3.size.Scale

private const val DEFAULT_ANIMATION_DURATION = 400

fun coverModel(
    coverUrl: String?,
    context: PlatformContext,
    animationMillis: Int = DEFAULT_ANIMATION_DURATION,
): ImageRequest = ImageRequest.Builder(context)
    .data(coverUrl)
    .networkCachePolicy(CachePolicy.ENABLED)
    .diskCachePolicy(CachePolicy.ENABLED)
    .memoryCachePolicy(CachePolicy.ENABLED)
    .precision(Precision.EXACT)
    .scale(Scale.FILL)
    .crossfade(animationMillis)
    .build()