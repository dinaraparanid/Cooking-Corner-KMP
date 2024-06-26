package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.feature.main.home.domain.RecipeUiState
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.theme.AppTheme

private val COVER_HEIGHT = 100.dp

@Composable
internal fun RecipeItem(
    recipe: RecipeUiState,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = ConstraintLayout(modifier) {
    val appPadding = AppTheme.dimensions.padding

    val (
        cover,
        title,
        ratingTimeRow,
        author,
        favouritesButton,
    ) = createRefs()

    fun ConstrainScope.fillMaxWidthWithPadding() {
        start.linkTo(parent.start, margin = appPadding.small)
        end.linkTo(parent.end, margin = appPadding.small)
        width = Dimension.fillToConstraints
    }

    RecipeClippedCover(
        coverUrlState = recipe.coverUrlState,
        modifier = Modifier
            .height(COVER_HEIGHT)
            .constrainAs(cover) {
                top.linkTo(parent.top, margin = appPadding.medium)
                fillMaxWidthWithPadding()
            },
    )

    RecipeTitle(
        title = recipe.title,
        modifier = Modifier.constrainAs(title) {
            top.linkTo(cover.bottom, margin = appPadding.small)
            bottom.linkTo(ratingTimeRow.top, margin = appPadding.small)
            fillMaxWidthWithPadding()
        }
    )

    RecipeRatingTimeRow(
        rating = recipe.rating,
        totalTime = recipe.totalTime,
        modifier = Modifier.constrainAs(ratingTimeRow) {
            bottom.linkTo(author.top, margin = appPadding.small)
            fillMaxWidthWithPadding()
        }
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))

    RecipeAuthor(
        author = recipe.author,
        modifier = Modifier.constrainAs(author) {
            bottom.linkTo(favouritesButton.top, margin = appPadding.small)
            fillMaxWidthWithPadding()
        },
    )

    FavouritesButton(
        isLiked = recipe.isLiked,
        onLikedChanged = { onUiIntent(UiIntent.LikeClick) },
        modifier = Modifier.constrainAs(favouritesButton) {
            bottom.linkTo(parent.bottom, margin = appPadding.extraMedium)
            fillMaxWidthWithPadding()
        }
    )
}

@Composable
private fun RecipeTitle(
    title: String,
    modifier: Modifier = Modifier,
) = Box(modifier) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.h.h3,
        fontFamily = AppTheme.typography.RalewayFontFamily,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterStart),
    )
}

@Composable
private fun RecipeClippedCover(
    coverUrlState: UiState<String>,
    modifier: Modifier = Modifier
) {
    val coverShape = RoundedCornerShape(AppTheme.dimensions.corners.small)

    RecipeCover(
        coverUrlState = coverUrlState,
        modifier = modifier
            .clip(coverShape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = coverShape,
            )
    )
}