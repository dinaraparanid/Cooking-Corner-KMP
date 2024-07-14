package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeState
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeUiIntent
import com.paranid5.cooking_corner.feature.main.recipe.presentation.RecipeClippedCover
import com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed.pager.RecipePager
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.RecipeDetailedUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.getOrThrow
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

    RecipeDetailsContent(
        state = state,
        onUiIntent = onUiIntent,
        modifier = modifier,
    )
}

@Composable
private fun RecipeDetailsContent(
    state: RecipeState,
    onUiIntent: (RecipeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Box(modifier) {
    @Composable
    fun Content(recipeUiState: RecipeDetailedUiState) = RecipeDetailsContentData(
        isOwned = state.isOwned,
        recipeUiState = recipeUiState,
        isKebabMenuVisible = state.isKebabMenuVisible,
        onUiIntent = onUiIntent,
        modifier = Modifier.fillMaxSize(),
    )

    @Composable
    fun Loader() = AppProgressIndicator(modifier = Modifier.align(Alignment.Center))

    when (val recipeUiState = state.recipeUiState) {
        is UiState.Data -> Content(recipeUiState.getOrThrow())

        is UiState.Error -> AppMainText(
            text = "TODO: Error Stub",
            style = AppTheme.typography.h.h1,
            modifier = Modifier.align(Alignment.Center)
        )

        is UiState.Loading, is UiState.Undefined -> Loader()

        is UiState.Success -> error("Illegal state: UiState.Success")

        is UiState.Refreshing -> recipeUiState
            .getOrNull()
            ?.let { Content(recipeUiState = it) }
            ?: Loader()
    }
}

@Composable
fun RecipeDetailsContentData(
    isOwned: Boolean,
    recipeUiState: RecipeDetailedUiState,
    isKebabMenuVisible: Boolean,
    onUiIntent: (RecipeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val fillMaxWidthWithPaddingModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = AppTheme.dimensions.padding.large)

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

        RecipeTopBar(
            isOwned = isOwned,
            recipeUiState = recipeUiState,
            isKebabMenuVisible = isKebabMenuVisible,
            onUiIntent = onUiIntent,
            modifier = fillMaxWidthWithPaddingModifier,
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        RecipeClippedCover(
            coverUrlState = recipeUiState.coverUrlState,
            onErrorButtonClick = {}, // TODO: Error handle
            modifier = Modifier
                .size(width = COVER_WIDTH, height = COVER_HEIGHT)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.big))

        RecipeTitle(
            title = recipeUiState.title,
            modifier = fillMaxWidthWithPaddingModifier,
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

        RatingReviewsAuthor(
            rating = recipeUiState.rating,
            reviews = recipeUiState.reviews,
            author = recipeUiState.author,
            modifier = fillMaxWidthWithPaddingModifier,
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.extraMedium))

        RatingOrCookingDetails(
            isOwned = isOwned,
            recipeUiState = recipeUiState,
            onUiIntent = onUiIntent,
            modifier = fillMaxWidthWithPaddingModifier,
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.extraSmall))

        RecipePager(
            steps = recipeUiState.steps,
            ingredients = recipeUiState.ingredients,
            modifier = Modifier.fillMaxWidth(),
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
    RecipeRating(
        rating = rating,
        modifier = Modifier.align(Alignment.CenterVertically),
    )

    Spacer(Modifier.width(AppTheme.dimensions.padding.medium))

    RecipeReviews(
        reviews = reviews,
        modifier = Modifier.align(Alignment.CenterVertically),
    )

    Spacer(Modifier.width(AppTheme.dimensions.padding.medium))

    RecipeAuthor(
        author = author,
        modifier = Modifier
            .weight(1F)
            .align(Alignment.CenterVertically),
    )
}

@Composable
private fun RatingOrCookingDetails(
    isOwned: Boolean,
    recipeUiState: RecipeDetailedUiState,
    onUiIntent: (RecipeUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = when {
    isOwned -> CookingDetails(
        modifier = modifier,
        preparingTime = recipeUiState.preparingTime,
        cookingTime = recipeUiState.cookingTime,
        portions = recipeUiState.portions,
    )

    else -> {
        val errorSnackbar = ErrorSnackbar()
        RatingSelector(
            modifier = modifier,
            myRating = recipeUiState.myRating,
            onStarClick = { rating ->
                onUiIntent(
                    RecipeUiIntent.Rate(
                        rating = rating,
                        errorSnackbar = errorSnackbar,
                    )
                )
            },
        )
    }
}

@Composable
private fun ErrorSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.something_went_wrong),
    snackbarType = SnackbarType.NEGATIVE,
)
