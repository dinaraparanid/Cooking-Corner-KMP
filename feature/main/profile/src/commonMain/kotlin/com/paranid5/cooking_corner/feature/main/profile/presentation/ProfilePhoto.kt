package com.paranid5.cooking_corner.feature.main.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.placeholder_profile
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.coverModel
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun ProfilePhoto(
    photoUrlState: UiState<String>,
    modifier: Modifier = Modifier,
) = SubcomposeAsyncImage(
    modifier = modifier,
    model = coverModel(coverUrl = photoUrlState.getOrNull()),
    contentDescription = null,
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop,
    loading = { AppProgressIndicator(Modifier.fillMaxSize()) },
    error = {
        ProfilePhotoThumbnail(
            photoUrlState = photoUrlState,
            modifier = Modifier.fillMaxSize(),
        )
    }
)

@Composable
private fun ProfilePhotoUndefinedPlaceholder(modifier: Modifier = Modifier) =
    Box(modifier = modifier.background(AppTheme.colors.background)) {
        Image(
            imageVector = vectorResource(Res.drawable.placeholder_profile),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
        )
    }

@Composable
private fun ProfilePhotoErrorPlaceholder(modifier: Modifier = Modifier) =
    Text("TODO: ProfilePhoto error placeholder", modifier)

@Composable
private fun ProfilePhotoThumbnail(
    photoUrlState: UiState<String>,
    modifier: Modifier = Modifier,
) = when (photoUrlState) {
    is UiState.Undefined,
    is UiState.Data -> ProfilePhotoUndefinedPlaceholder(modifier)

    is UiState.Error -> ProfilePhotoErrorPlaceholder(modifier)

    is UiState.Loading,
    is UiState.Refreshing -> AppProgressIndicator(modifier)
}