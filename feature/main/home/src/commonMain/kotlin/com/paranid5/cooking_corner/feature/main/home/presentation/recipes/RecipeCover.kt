package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.placeholder_recipe
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.coverModel
import com.paranid5.cooking_corner.ui.getOrNull
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun RecipeCover(
    coverUrlState: UiState<String>,
    modifier: Modifier = Modifier,
) = SubcomposeAsyncImage(
    modifier = modifier,
    model = coverModel(coverUrl = coverUrlState.getOrNull()),
    contentDescription = null,
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop,
    loading = { AppProgressIndicator(Modifier.fillMaxSize()) },
    error = {
        RecipeThumbnail(
            coverUrlState = coverUrlState,
            modifier = Modifier.fillMaxSize(),
        )
    }
)

@Composable
private fun RecipeUndefinedPlaceholder(modifier: Modifier = Modifier) =
    Box(modifier = modifier) {
        Image(
            imageVector = vectorResource(Res.drawable.placeholder_recipe),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
        )
    }

@Composable
private fun RecipeErrorPlaceholder(modifier: Modifier = Modifier) =
    Text("TODO: Recipe error placeholder", modifier)

@Composable
private fun RecipeThumbnail(
    coverUrlState: UiState<String>,
    modifier: Modifier = Modifier,
) = when (coverUrlState) {
    is UiState.Undefined,
    is UiState.Data -> RecipeUndefinedPlaceholder(modifier)

    is UiState.Error -> RecipeErrorPlaceholder(modifier)

    is UiState.Loading,
    is UiState.Refreshing -> AppProgressIndicator(modifier)
}