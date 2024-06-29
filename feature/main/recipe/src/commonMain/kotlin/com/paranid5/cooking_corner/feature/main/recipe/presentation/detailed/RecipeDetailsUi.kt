package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_cooking_time
import com.paranid5.cooking_corner.core.resources.recipe_portions
import com.paranid5.cooking_corner.core.resources.recipe_portions_value
import com.paranid5.cooking_corner.core.resources.recipe_prep_time
import com.paranid5.cooking_corner.core.resources.unit_minute
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent
import com.paranid5.cooking_corner.feature.main.recipe.presentation.RecipeClippedCover
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

private val COVER_WIDTH = 325.dp
private val COVER_HEIGHT = 185.dp

@Composable
fun RecipeDetailsUi(
    component: RecipeComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    val fillMaxWidthWithPaddingModifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.large)

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

        TopBar(
            onUiIntent = onUiIntent,
            modifier = fillMaxWidthWithPaddingModifier,
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        RecipeClippedCover(
            coverUrlState = state.recipe.coverUrlState,
            modifier = Modifier
                .size(width = COVER_WIDTH, height = COVER_HEIGHT)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.big))

        RecipeTitle(
            title = state.recipe.title,
            modifier = fillMaxWidthWithPaddingModifier,
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

        RatingReviewsAuthor(
            rating = state.recipe.rating,
            reviews = state.recipe.reviews,
            author = state.recipe.author,
            modifier = fillMaxWidthWithPaddingModifier,
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.extraMedium))

        TimesAndPortions(
            preparingTime = state.recipe.preparingTime,
            cookingTime = state.recipe.cookingTime,
            portions = state.recipe.portions,
            modifier = fillMaxWidthWithPaddingModifier,
        )
    }
}

@Composable
private fun RecipeTitle(
    title: String,
    modifier: Modifier = Modifier,
) = Text(
    text = title,
    modifier = modifier,
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h2,
    fontFamily = AppTheme.typography.RalewayFontFamily,
)

@Composable
private fun RatingReviewsAuthor(
    rating: Float,
    reviews: Int,
    author: String,
    modifier: Modifier = Modifier,
) = Row(modifier) {
    Rating(
        rating = rating,
        modifier = Modifier.align(Alignment.CenterVertically),
    )

    Spacer(Modifier.width(AppTheme.dimensions.padding.medium))

    Reviews(
        reviews = reviews,
        modifier = Modifier.align(Alignment.CenterVertically),
    )

    Spacer(Modifier.width(AppTheme.dimensions.padding.medium))

    Author(
        author = author,
        modifier = Modifier
            .weight(1F)
            .align(Alignment.CenterVertically),
    )
}

@Composable
private fun TimesAndPortions(
    preparingTime: Int,
    cookingTime: Int,
    portions: Int,
    modifier: Modifier = Modifier,
) = Row(modifier) {
    LabelWithDescription(
        title = stringResource(Res.string.recipe_prep_time),
        description = "$preparingTime ${stringResource(Res.string.unit_minute)}",
        modifier = Modifier.weight(1F),
    )

    LabelWithDescription(
        title = stringResource(Res.string.recipe_cooking_time),
        description = "$cookingTime ${stringResource(Res.string.unit_minute)}",
        modifier = Modifier.weight(1F),
    )

    LabelWithDescription(
        title = stringResource(Res.string.recipe_portions),
        description = stringResource(Res.string.recipe_portions_value, portions),
        modifier = Modifier.weight(1F),
    )
}

@Composable
private fun LabelWithDescription(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Text(
        text = title,
        modifier = Modifier.align(Alignment.CenterHorizontally),
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.regular,
        fontFamily = AppTheme.typography.RalewayFontFamily,
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.minimum))

    Text(
        text = description,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.align(Alignment.CenterHorizontally),
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.body,
        fontFamily = AppTheme.typography.RalewayFontFamily,
    )
}
