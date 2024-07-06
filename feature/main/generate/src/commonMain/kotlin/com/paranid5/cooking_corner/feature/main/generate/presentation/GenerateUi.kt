package com.paranid5.cooking_corner.feature.main.generate.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.generate_apply_button
import com.paranid5.cooking_corner.core.resources.generate_link_placeholder
import com.paranid5.cooking_corner.core.resources.generate_provide_link
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateComponent
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateState
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateUiIntent
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedEditText
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedRippleButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun GenerateUi(
    component: GenerateComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    Column(modifier.background(AppTheme.colors.background.primary)) {
        GenerateTopBar(
            onUiIntent = onUiIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimensions.padding.small)
                .padding(horizontal = AppTheme.dimensions.padding.large),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun GenerateLabel(modifier: Modifier = Modifier) = Text(
    modifier = modifier,
    text = stringResource(Res.string.generate_provide_link),
    fontWeight = FontWeight.Bold,
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.regular,
    fontFamily = AppTheme.typography.RalewayFontFamily,
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
    onValueChange = { onUiIntent(GenerateUiIntent.UpdateLink(link = it)) }
)

@Composable
private fun GenerateButton(
    onUiIntent: (GenerateUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = AppOutlinedRippleButton(
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
    onClick = { onUiIntent(GenerateUiIntent.GenerateClick) },
) {
    Text(
        text = stringResource(Res.string.generate_apply_button),
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.h.h2,
        fontFamily = AppTheme.typography.InterFontFamily,
    )
}
