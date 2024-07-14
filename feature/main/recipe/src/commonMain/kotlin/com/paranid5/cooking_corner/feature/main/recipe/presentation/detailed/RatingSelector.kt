package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_rating_star_empty
import com.paranid5.cooking_corner.core.resources.ic_rating_star_filled
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.clickableWithRipple
import org.jetbrains.compose.resources.vectorResource
import kotlin.math.roundToInt

private val NUMBER_OF_STARS = 5

@Composable
internal fun RatingSelector(
    myRating: Float?,
    onStarClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shownRating = remember(myRating) {
        myRating?.roundToInt() ?: 0
    }

    Column(modifier) {
        RatingDivider(Modifier.fillMaxWidth())

        Row(
            modifier = Modifier
                .padding(vertical = AppTheme.dimensions.padding.small)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.large),
        ) {
            (0 until shownRating).forEach { index ->
                RatingStar(isFilled = true) { onStarClick(index + 1) }
            }

            (shownRating until NUMBER_OF_STARS).forEach { index ->
                RatingStar(isFilled = false) { onStarClick(index + 1) }
            }
        }

        RatingDivider(Modifier.fillMaxWidth())
    }
}

@Composable
private fun RatingStar(
    isFilled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val starRes = remember(isFilled) {
        when {
            isFilled -> Res.drawable.ic_rating_star_filled
            else -> Res.drawable.ic_rating_star_empty
        }
    }

    Image(
        imageVector = vectorResource(starRes),
        contentDescription = null,
        modifier = modifier.clickableWithRipple(onClick = onClick),
    )
}

@Composable
private fun RatingDivider(modifier: Modifier = Modifier) = Divider(
    modifier = modifier,
    color = AppTheme.colors.button.primary,
    thickness = AppTheme.dimensions.borders.minimum,
)
