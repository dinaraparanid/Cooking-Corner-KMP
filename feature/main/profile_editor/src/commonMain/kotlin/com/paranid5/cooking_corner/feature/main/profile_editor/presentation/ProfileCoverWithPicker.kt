package com.paranid5.cooking_corner.feature.main.profile_editor.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_photo
import com.paranid5.cooking_corner.core.resources.placeholder_profile
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.data
import com.paranid5.cooking_corner.ui.foundation.AppErrorPlaceholder
import com.paranid5.cooking_corner.ui.foundation.AppIconButton
import com.paranid5.cooking_corner.ui.foundation.AppLoadingBox
import com.paranid5.cooking_corner.ui.foundation.coverModel
import com.paranid5.cooking_corner.ui.foundation.picker.ImagePickerLauncher
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

private val IMAGE_SIZE = 200.dp

@Composable
internal fun ProfileCoverWithPicker(
    cover: ImageContainer?,
    modifier: Modifier = Modifier,
    onPicked: (ByteArray) -> Unit,
) {
    val launchPicker = ImagePickerLauncher(onPicked)

    Box(modifier) {
        SubcomposeAsyncImage(
            model = coverModel(data = cover?.data),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(IMAGE_SIZE)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .border(
                    width = AppTheme.dimensions.borders.minimum,
                    color = AppTheme.colors.button.primary,
                    shape = CircleShape,
                ),
            loading = {
                AppLoadingBox(
                    isLoading = true,
                    modifier = Modifier.fillMaxSize(),
                )
            },
            error = {
                AppErrorPlaceholder(
                    image = vectorResource(Res.drawable.placeholder_profile),
                    modifier = Modifier.fillMaxSize(),
                )
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
