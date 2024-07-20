package com.paranid5.cooking_corner.feature.main.home.presentation.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_add
import com.paranid5.cooking_corner.core.resources.ic_ascending_filter
import com.paranid5.cooking_corner.core.resources.ic_descending_filter
import com.paranid5.cooking_corner.core.resources.ic_generate
import com.paranid5.cooking_corner.core.resources.ic_like
import com.paranid5.cooking_corner.core.resources.ic_liked
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.State
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.ui.foundation.AppIconButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.vectorResource

private val ICON_SIZE = 32.dp
private val CATEGORY_SPINNER_MIN_HEIGHT = 52.dp

@Composable
internal fun HomeTopBar(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    TopBarBorder(Modifier.fillMaxWidth())

    HomeTopBarImpl(
        state = state,
        onUiIntent = onUiIntent,
        modifier = Modifier.fillMaxWidth()
    )

    TopBarBorder(Modifier.fillMaxWidth())
}

@Composable
private fun HomeTopBarImpl(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spinnerShape = RoundedCornerShape(AppTheme.dimensions.corners.small)
    val margin = AppTheme.dimensions.padding.extraSmall

    Row(
        horizontalArrangement = Arrangement.spacedBy(margin),
        modifier = modifier
            .background(AppTheme.colors.background.primaryDarker)
            .padding(horizontal = margin)
    ) {
        AppIconButton(
            icon = vectorResource(Res.drawable.ic_add),
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onUiIntent(UiIntent.AddRecipe) },
        )

        AppIconButton(
            icon = vectorResource(Res.drawable.ic_generate),
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onUiIntent(UiIntent.GenerateRecipe) },
        )

        CategorySpinner(
            state = state,
            onUiIntent = onUiIntent,
            modifier = Modifier
                .weight(1F)
                .align(Alignment.CenterVertically)
                .padding(vertical = AppTheme.dimensions.padding.small)
                .heightIn(min = CATEGORY_SPINNER_MIN_HEIGHT)
                .clip(spinnerShape)
                .border(
                    width = AppTheme.dimensions.borders.minimum,
                    color = AppTheme.colors.button.primary,
                    shape = spinnerShape,
                ),
        )

        OrderButton(
            isAscendingOrder = state.isAscendingOrder,
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onUiIntent(UiIntent.OrderClick) },
        )

        LikeButton(
            isLiked = state.areFavouritesShown,
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { onUiIntent(UiIntent.UpdateFavouritesShown) },
        )
    }
}

@Composable
private fun TopBarBorder(modifier: Modifier = Modifier) = Spacer(
    modifier
        .height(AppTheme.dimensions.borders.minimum)
        .background(AppTheme.colors.button.primary)
)

@Composable
private fun OrderButton(
    isAscendingOrder: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val iconRes = remember(isAscendingOrder) {
        when {
            isAscendingOrder -> Res.drawable.ic_ascending_filter
            else -> Res.drawable.ic_descending_filter
        }
    }

    AppIconButton(
        icon = vectorResource(iconRes),
        modifier = modifier,
        onClick = onClick,
    )
}

@Composable
private fun LikeButton(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val iconRes = remember(isLiked) {
        when {
            isLiked -> Res.drawable.ic_liked
            else -> Res.drawable.ic_like
        }
    }

    AppIconButton(
        icon = vectorResource(iconRes),
        tint = AppTheme.colors.orange,
        modifier = modifier,
        iconModifier = Modifier.size(ICON_SIZE),
        onClick = { onClick() },
    )
}
