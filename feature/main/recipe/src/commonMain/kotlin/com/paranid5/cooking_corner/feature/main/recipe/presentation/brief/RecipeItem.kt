package com.paranid5.cooking_corner.feature.main.recipe.presentation.brief

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.paranid5.cooking_corner.feature.main.recipe.presentation.RecipeClippedCover
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme

private val COVER_WIDTH = 166.dp
private val COVER_HEIGHT = 100.dp

@Composable
fun RecipeItem(
    recipe: RecipeUiState,
    modifier: Modifier = Modifier,
    onErrorButtonClick: (() -> Unit)? = null,
    actionButton: @Composable (Modifier) -> Unit,
) {
    val appPadding = AppTheme.dimensions.padding
    val shape = RoundedCornerShape(AppTheme.dimensions.corners.small)

    ConstraintLayout(
        modifier = modifier
            .clip(shape)
            .border(
                width = AppTheme.dimensions.borders.extraSmall,
                color = AppTheme.colors.button.primary,
                shape = shape,
            )
    ) {
        val (
            cover,
            title,
            ratingTimeRow,
            author,
            actButton,
        ) = createRefs()

        fun ConstrainScope.fillMaxWidthWithPadding() {
            start.linkTo(parent.start, margin = appPadding.small)
            end.linkTo(parent.end, margin = appPadding.small)
            width = Dimension.fillToConstraints
        }

        RecipeClippedCover(
            coverUrlState = recipe.coverUrlState,
            onErrorButtonClick = onErrorButtonClick,
            modifier = Modifier
                .size(width = COVER_WIDTH, height = COVER_HEIGHT)
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

        RecipeAuthor(
            author = recipe.author,
            modifier = Modifier.constrainAs(author) {
                bottom.linkTo(actButton.top, margin = appPadding.small)
                fillMaxWidthWithPadding()
            },
        )

        actionButton(
            Modifier.constrainAs(actButton) {
                bottom.linkTo(parent.bottom, margin = appPadding.extraMedium)
                fillMaxWidthWithPadding()
            }
        )
    }
}

@Composable
private fun RecipeTitle(
    title: String,
    modifier: Modifier = Modifier,
) = Box(modifier) {
    AppMainText(
        text = title,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        style = AppTheme.typography.h.h3,
        maxLines = 2,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterStart),
    )
}
