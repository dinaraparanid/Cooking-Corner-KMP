package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_photo
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.data
import com.paranid5.cooking_corner.ui.foundation.AppErrorPlaceholder
import com.paranid5.cooking_corner.ui.foundation.AppIconButton
import com.paranid5.cooking_corner.ui.foundation.AppLoadingBox
import com.paranid5.cooking_corner.ui.foundation.coverModel
import com.paranid5.cooking_corner.ui.foundation.picker.ImagePickerLauncher
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun RecipeCover(
    cover: ImageContainer,
    modifier: Modifier = Modifier,
    onPicked: (ByteArray) -> Unit,
) {
    val launchPicker = ImagePickerLauncher(onPicked)

    Box(modifier) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = coverModel(data = cover.data),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            loading = {
                AppLoadingBox(
                    isLoading = true,
                    modifier = Modifier.fillMaxSize()
                )
            },
            error = {
                AppErrorPlaceholder(Modifier.fillMaxSize())
            },
        )

        AppIconButton(
            icon = vectorResource(Res.drawable.ic_photo),
            tint = AppTheme.colors.orange,
            onClick = launchPicker,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = AppTheme.dimensions.padding.small)
                .background(
                    color = AppTheme.colors.button.primary.copy(alpha = 0.5F),
                    shape = CircleShape,
                ),
        )
    }
}