package com.paranid5.cooking_corner.feature.main.recipe.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.data
import com.paranid5.cooking_corner.ui.foundation.AppErrorPlaceholder
import com.paranid5.cooking_corner.ui.foundation.AppLoadingBox
import com.paranid5.cooking_corner.ui.foundation.coverModel
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
internal fun RecipeClippedCover(
    cover: ImageContainer?,
    modifier: Modifier = Modifier,
) {
    val coverShape = RoundedCornerShape(AppTheme.dimensions.corners.small)

    SubcomposeAsyncImage(
        model = coverModel(cover?.data),
        contentDescription = null,
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(coverShape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = coverShape,
            ),
        loading = {
            AppLoadingBox(
                isLoading = true,
                modifier = Modifier.fillMaxSize(),
            )
        },
        error = {
            AppErrorPlaceholder(Modifier.fillMaxSize())
        },
    )
}
