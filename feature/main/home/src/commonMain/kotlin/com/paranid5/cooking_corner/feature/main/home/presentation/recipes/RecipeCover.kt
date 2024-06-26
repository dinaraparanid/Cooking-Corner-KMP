package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.common.AppProgressIndicator
import com.paranid5.cooking_corner.ui.common.coverModel
import com.paranid5.cooking_corner.ui.getOrNull

@Composable
internal fun RecipeCover(
    coverUrlState: UiState<String>,
    modifier: Modifier = Modifier,
) = SubcomposeAsyncImage(
    modifier = modifier,
    model = coverModel(
        coverUrl = coverUrlState.getOrNull(),
        context = LocalPlatformContext.current,
    ),
    contentDescription = null,
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop,
    loading = { AppProgressIndicator(Modifier.fillMaxSize()) },
    error = {
        RecipeThumbnail(
            coverUrlState = coverUrlState,
            modifier = Modifier.fillMaxSize()
        )
    }
)

@Composable
private fun RecipePlaceholder(modifier: Modifier = Modifier) =
    Text("TODO: Recipe placeholder", modifier)

@Composable
private fun RecipeThumbnail(
    coverUrlState: UiState<String>,
    modifier: Modifier = Modifier,
) = when (coverUrlState) {
    is UiState.Data,
    is UiState.Error -> RecipePlaceholder(modifier)

    is UiState.Loading,
    is UiState.Refreshing,
    is UiState.Undefined -> AppProgressIndicator(modifier)
}