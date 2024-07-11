package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.home_add_to_favourites
import com.paranid5.cooking_corner.core.resources.home_remove_from_favourites
import com.paranid5.cooking_corner.core.resources.ic_like
import com.paranid5.cooking_corner.core.resources.ic_liked
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.feature.main.home.component.HomeStore.UiIntent
import com.paranid5.cooking_corner.ui.entity.RecipeUiState
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

private val ICON_SIZE = 21.dp

@Composable
internal fun FavouritesButton(
    recipeUiState: RecipeUiState,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val appPadding = AppTheme.dimensions.padding

    val unhandledErrorMessage = stringResource(Res.string.something_went_wrong)

    fun onLikeClick() = onUiIntent(
        UiIntent.LikeClick(
            recipeUiState = recipeUiState,
            unhandledErrorMessage = unhandledErrorMessage,
        )
    )

    fun onDislikeClick() = onUiIntent(
        UiIntent.DislikeClick(
            recipeUiState = recipeUiState,
            unhandledErrorMessage = unhandledErrorMessage,
        )
    )

    val onClick = remember(recipeUiState) {
        when {
            recipeUiState.isLiked -> ::onDislikeClick
            else -> ::onLikeClick
        }
    }

    AppOutlinedRippleButton(
        onClick = onClick,
        shape = RoundedCornerShape(AppTheme.dimensions.corners.small),
        border = BorderStroke(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.button.secondary,
            disabledContainerColor = AppTheme.colors.button.secondary,
        ),
        modifier = modifier.simpleShadow(
            radius = AppTheme.dimensions.corners.small,
        ),
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            val (label, image) = createRefs()

            FavouritesButtonLabel(
                isLiked = recipeUiState.isLiked,
                modifier = Modifier.constrainAs(label) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start, margin = appPadding.extraSmall)
                    end.linkTo(image.start, margin = appPadding.extraSmall)
                    width = Dimension.fillToConstraints
                },
            )

            FavouritesButtonImage(
                isLiked = recipeUiState.isLiked,
                modifier = Modifier
                    .size(ICON_SIZE)
                    .constrainAs(image) {
                        centerVerticallyTo(parent)
                        end.linkTo(parent.end)
                    },
            )
        }
    }
}

@Composable
private fun FavouritesButtonLabel(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
) = when {
    isLiked -> FavouritesButtonLabelImpl(
        text = stringResource(Res.string.home_remove_from_favourites),
        modifier = modifier,
    )

    else -> FavouritesButtonLabelImpl(
        text = stringResource(Res.string.home_add_to_favourites),
        modifier = modifier,
    )
}

@Composable
private fun FavouritesButtonLabelImpl(
    text: String,
    modifier: Modifier = Modifier,
) = Text(
    text = text,
    modifier = modifier,
    color = AppTheme.colors.text.primary,
    fontWeight = FontWeight.Bold,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    style = AppTheme.typography.captionSm,
)

@Composable
private fun FavouritesButtonImage(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
) = when {
    isLiked -> FavouritesButtonImageImpl(
        icon = vectorResource(Res.drawable.ic_liked),
        modifier = modifier,
    )

    else -> FavouritesButtonImageImpl(
        icon = vectorResource(Res.drawable.ic_like),
        modifier = modifier,
    )
}

@Composable
private fun FavouritesButtonImageImpl(
    icon: ImageVector,
    modifier: Modifier = Modifier,
) = Image(
    imageVector = icon,
    contentDescription = null,
    modifier = modifier,
)