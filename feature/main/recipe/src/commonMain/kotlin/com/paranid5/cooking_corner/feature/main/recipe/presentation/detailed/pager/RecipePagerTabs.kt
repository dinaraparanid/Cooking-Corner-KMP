package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed.pager

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_cooking_steps
import com.paranid5.cooking_corner.core.resources.recipe_ingredients
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import com.paranid5.cooking_corner.ui.utils.pxToDp
import org.jetbrains.compose.resources.stringResource

private val PagerTabShape
    @Composable
    get() = RoundedCornerShape(AppTheme.dimensions.corners.medium)

internal inline val PagerItemShape
    @Composable
    get() = RoundedCornerShape(AppTheme.dimensions.corners.extraMedium)

@Composable
internal fun RecipePagerTabs(
    activePage: Int,
    modifier: Modifier = Modifier,
    onTabClick: (Int) -> Unit,
) {
    var pagerWidth by remember { mutableIntStateOf(1) }
    var pagerHeight by remember { mutableIntStateOf(1) }

    Box(modifier) {
        RecipePagerTabsContent(
            onTabClick = onTabClick,
            modifier = Modifier
                .zIndex(1F)
                .fillMaxWidth()
                .clip(PagerTabShape)
                .border(
                    width = AppTheme.dimensions.borders.extraSmall,
                    color = AppTheme.colors.button.primary,
                    shape = PagerTabShape,
                )
                .onGloballyPositioned { coords ->
                    pagerWidth = coords.size.width
                    pagerHeight = coords.size.height
                }
        )

        RecipePagerActiveZone(
            modifier = Modifier
                .width((pagerWidth / 2).pxToDp())
                .height(pagerHeight.pxToDp())
                .activePagerTab(activePage, pagerWidthPx = pagerWidth),
        )
    }
}

@Composable
private fun RecipePagerTabsContent(
    modifier: Modifier = Modifier,
    onTabClick: (Int) -> Unit,
) = Row(modifier = modifier) {
    RecipePagerTab(
        text = stringResource(Res.string.recipe_cooking_steps),
        modifier = Modifier
            .weight(1F)
            .clickableWithRipple { onTabClick(PAGE_STEPS) },
    )

    RecipePagerTab(
        text = stringResource(Res.string.recipe_ingredients),
        modifier = Modifier
            .weight(1F)
            .clickableWithRipple { onTabClick(PAGE_INGREDIENTS) },
    )
}

@Composable
private fun RecipePagerTab(
    text: String,
    modifier: Modifier = Modifier,
) = Box(
    Modifier
        .clip(PagerTabShape)
        .then(modifier)
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.body,
        fontFamily = AppTheme.typography.RalewayFontFamily,
        modifier = Modifier
            .align(Alignment.Center)
            .padding(AppTheme.dimensions.padding.medium),
    )
}

@Composable
private fun RecipePagerActiveZone(modifier: Modifier = Modifier) = Spacer(
    modifier
        .clip(PagerTabShape)
        .border(
            width = AppTheme.dimensions.borders.extraSmall,
            color = AppTheme.colors.button.primary,
            shape = PagerTabShape,
        )
        .background(
            color = AppTheme.colors.background.primaryDarkest,
            shape = PagerTabShape,
        )
)

@Composable
private fun Modifier.activePagerTab(
    activePage: Int,
    pagerWidthPx: Int,
): Modifier {
    val xOffset by animateDpAsState(
        when (activePage) {
            PAGE_STEPS -> 0.dp
            PAGE_INGREDIENTS -> (pagerWidthPx / 2).pxToDp()
            else -> error("Illegal page number")
        }
    )

    return offset(x = xOffset, y = 0.dp)
}