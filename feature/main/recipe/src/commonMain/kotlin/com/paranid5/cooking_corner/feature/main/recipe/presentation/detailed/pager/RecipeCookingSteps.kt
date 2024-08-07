package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed.pager

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_no_steps_description
import com.paranid5.cooking_corner.core.resources.recipe_no_steps_title
import com.paranid5.cooking_corner.core.resources.recipe_step
import com.paranid5.cooking_corner.feature.main.recipe.presentation.RecipeClippedCover
import com.paranid5.cooking_corner.ui.entity.recipe.StepUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
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
    when {
        steps.isEmpty() -> NoPagerItemsPlaceholder(
            title = stringResource(Res.string.recipe_no_steps_title),
            description = stringResource(Res.string.recipe_no_steps_description),
            modifier = Modifier.fillMaxWidth(),
        )

        else -> steps.forEachIndexed { index, step ->
            RecipeCookingStep(
                stepNumber = index + 1,
                stepUiState = step,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    Spacer(Modifier.height(AppTheme.dimensions.padding.small))
}

@Composable
private fun RecipeCookingStep(
    stepNumber: Int,
    stepUiState: StepUiState,
    modifier: Modifier = Modifier,
) {
    val shape = PagerItemShape

    val stepTitle = stepUiState.title.takeIf(String::isNotBlank)
        ?: stringResource(Res.string.recipe_step, stepNumber)

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
            stepTitle = stepTitle,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        RecipeClippedCover(
            cover = stepUiState.cover,
            modifier = Modifier
                .size(width = COVER_WIDTH, height = COVER_HEIGHT)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))

        RecipeStepDescription(
            description = stepUiState.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.padding.extraMedium),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.small))
    }
}

@Composable
private fun RecipeStepLabel(
    stepTitle: String,
    modifier: Modifier = Modifier,
) = AppMainText(
    text = stepTitle,
    fontWeight = FontWeight.Bold,
    style = AppTheme.typography.body,
    modifier = modifier,
)

@Composable
private fun RecipeStepDescription(
    description: String,
    modifier: Modifier = Modifier,
) = AppMainText(
    text = description,
    textAlign = TextAlign.Center,
    style = AppTheme.typography.body,
    modifier = modifier,
)