package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed.pager

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_step
import com.paranid5.cooking_corner.ui.entity.StepUiState
import com.paranid5.cooking_corner.feature.main.recipe.presentation.RecipeClippedCover
import com.paranid5.cooking_corner.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

private val COVER_WIDTH = 232.dp
private val COVER_HEIGHT = 133.dp

@Composable
internal fun RecipeCookingSteps(
    steps: ImmutableList<StepUiState>,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.medium),
) {
    steps.forEachIndexed { index, step ->
        RecipeCookingStep(
            stepNumber = index + 1,
            stepUiState = step,
            modifier = Modifier.fillMaxWidth(),
        )
    }

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))
}

@Composable
private fun RecipeCookingStep(
    stepNumber: Int,
    stepUiState: StepUiState,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(AppTheme.dimensions.corners.extraMedium)

    Column(
        modifier = modifier
            .clip(shape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = shape,
            )
    ) {
        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        RecipeStepLabel(
            stepNumber = stepNumber,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        RecipeClippedCover(
            coverUrlState = stepUiState.coverUrlState,
            onErrorButtonClick = {}, // TODO: Error handle
            modifier = Modifier
                .size(width = COVER_WIDTH, height = COVER_HEIGHT)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        RecipeStepDescription(
            description = stepUiState.text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.padding.extraMedium),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))
    }
}

@Composable
private fun RecipeStepLabel(
    stepNumber: Int,
    modifier: Modifier = Modifier,
) = Text(
    text = stringResource(Res.string.recipe_step, stepNumber),
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.body,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)

@Composable
private fun RecipeStepDescription(
    description: String,
    modifier: Modifier = Modifier,
) = Text(
    text = description,
    textAlign = TextAlign.Center,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.body,
    fontFamily = AppTheme.typography.RalewayFontFamily,
    modifier = modifier,
)