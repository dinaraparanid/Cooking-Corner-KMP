package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_heart
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeUiIntent
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

private val BUTTON_SIZE = 36.dp

@Composable
internal fun TopBar(
    isLiked: Boolean,
    onUiIntent: (RecipeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Box(modifier) {
    BackButton(
        onClick = { onUiIntent(RecipeUiIntent.Back) },
        modifier = Modifier
            .size(BUTTON_SIZE)
            .align(Alignment.CenterStart),
    )

    LikeButton(
        isLiked = isLiked,
        onClick = { onUiIntent(RecipeUiIntent.LikeClick) },
        modifier = Modifier
            .size(BUTTON_SIZE)
            .align(Alignment.CenterEnd),
    )
}

@Composable
private fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = TopButton(
    onClick = onClick,
    modifier = modifier,
) {
    BackIcon(Modifier.align(Alignment.Center))
}

@Composable
private fun LikeButton(
    isLiked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = TopButton(
    onClick = onClick,
    modifier = modifier,
) {
    LikeIcon(
        Modifier
            .align(Alignment.Center)
            .zIndex(1F)
    )

    Spacer(
        Modifier
            .fillMaxSize()
            .padding(AppTheme.dimensions.padding.minimum)
            .background(if (isLiked) AppTheme.colors.orange else Color.Transparent),
    )
}

@Composable
private fun BackIcon(modifier: Modifier = Modifier) = Image(
    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
    contentDescription = null,
    modifier = modifier,
)

@Composable
private fun LikeIcon(modifier: Modifier = Modifier) = Image(
    imageVector = vectorResource(Res.drawable.ic_heart),
    contentDescription = null,
    modifier = modifier,
)