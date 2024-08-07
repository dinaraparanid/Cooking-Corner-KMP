package com.paranid5.cooking_corner.feature.main.generate.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.generate_apply_button
import com.paranid5.cooking_corner.core.resources.generate_generation_error
import com.paranid5.cooking_corner.core.resources.generate_link_placeholder
import com.paranid5.cooking_corner.core.resources.generate_provide_link
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateState
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateUiIntent
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedEditText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun UrlEditor(
    state: GenerateState,
    onUiIntent: (GenerateUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    GenerateLabel(Modifier.padding(horizontal = AppTheme.dimensions.padding.big))

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    GenerateLinkInput(
        state = state,
        onUiIntent = onUiIntent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.big),
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    GenerateButton(
        onUiIntent = onUiIntent,
        modifier = Modifier.align(Alignment.CenterHorizontally),
    )
}

@Composable
private fun GenerateLabel(modifier: Modifier = Modifier) = AppMainText(
    modifier = modifier,
    text = stringResource(Res.string.generate_provide_link),
    fontWeight = FontWeight.Bold,
    style = AppTheme.typography.regular,
)

@Composable
private fun GenerateLinkInput(
    state: GenerateState,
    onUiIntent: (GenerateUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = AppOutlinedEditText(
    modifier = modifier,
    value = state.link,
    placeholder = stringResource(Res.string.generate_link_placeholder),
    onValueChange = { onUiIntent(GenerateUiIntent.UpdateLink(link = it)) },
)

@Composable
private fun GenerateButton(
    onUiIntent: (GenerateUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val generationErrorSnackbar = GenerationErrorSnackbar()

    AppOutlinedRippleButton(
        modifier = modifier,
        shape = RoundedCornerShape(AppTheme.dimensions.corners.medium),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.button.secondary,
            disabledContainerColor = AppTheme.colors.button.secondary,
        ),
        border = BorderStroke(
            width = AppTheme.dimensions.borders.minimum,
            color = AppTheme.colors.button.primary,
        ),
        onClick = { onUiIntent(GenerateUiIntent.GenerateClick(generationErrorSnackbar)) },
    ) {
        AppMainText(
            text = stringResource(Res.string.generate_apply_button),
            style = AppTheme.typography.h.h2,
        )
    }
}

@Composable
private fun GenerationErrorSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.generate_generation_error),
    snackbarType = SnackbarType.NEGATIVE,
)
