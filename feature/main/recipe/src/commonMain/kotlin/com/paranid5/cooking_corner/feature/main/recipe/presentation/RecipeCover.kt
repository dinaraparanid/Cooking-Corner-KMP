package com.paranid5.cooking_corner.feature.main.recipe.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.placeholder_recipe
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.foundation.AppLoadingBox
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.coverModel
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.isUndefinedOrLoading
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun RecipeClippedCover(
    coverUrlState: UiState<String>,
    modifier: Modifier = Modifier,
    onErrorButtonClick: (() -> Unit)? = null,
) {
    val coverShape = RoundedCornerShape(AppTheme.dimensions.corners.small)

    AppLoadingBox(
        isLoading = coverUrlState.isUndefinedOrLoading,
        isError = coverUrlState is UiState.Error,
        onErrorButtonClick = onErrorButtonClick,
        modifier = modifier
            .clip(coverShape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = coverShape,
            ),
    ) {
        RecipeCover(
            coverUrlState = coverUrlState,
            modifier = Modifier.fillMaxSize()
        )
    }
}

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
private fun RecipePlaceholder(modifier: Modifier = Modifier) =
    Box(modifier = modifier) {
        Image(
            imageVector = vectorResource(Res.drawable.placeholder_recipe),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
        )
    }

@Composable
private fun RecipeThumbnail(
    coverUrlState: UiState<String>,
    modifier: Modifier = Modifier,
) = when (coverUrlState) {
    is UiState.Undefined,
    is UiState.Data,
    is UiState.Success,
    is UiState.Error -> RecipePlaceholder(modifier)

    is UiState.Loading,
    is UiState.Refreshing -> AppProgressIndicator(modifier)
}