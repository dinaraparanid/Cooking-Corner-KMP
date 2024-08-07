package com.paranid5.cooking_corner.feature.main.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.placeholder_profile
import com.paranid5.cooking_corner.ui.foundation.AppLoadingBox
import com.paranid5.cooking_corner.ui.foundation.coverModel
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

@NonRestartableComposable
@Composable
internal fun ProfileCover(
    cover: Any?,
    modifier: Modifier = Modifier,
) = SubcomposeAsyncImage(
    modifier = modifier,
    model = coverModel(data = cover),
    contentDescription = null,
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop,
    loading = { AppLoadingBox(isLoading = true, modifier = Modifier.fillMaxSize()) },
    error = { ProfilePhotoPlaceholder(Modifier.fillMaxSize()) },
)

@Composable
private fun ProfilePhotoPlaceholder(modifier: Modifier = Modifier) =
    Box(modifier = modifier.background(AppTheme.colors.background.primary)) {
        Image(
            imageVector = vectorResource(Res.drawable.placeholder_profile),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
        )
    }
