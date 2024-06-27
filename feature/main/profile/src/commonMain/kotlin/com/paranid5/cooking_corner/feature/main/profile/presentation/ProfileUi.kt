package com.paranid5.cooking_corner.feature.main.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.profile_cooking_experience
import com.paranid5.cooking_corner.core.resources.profile_email
import com.paranid5.cooking_corner.core.resources.profile_name
import com.paranid5.cooking_corner.core.resources.profile_surname
import com.paranid5.cooking_corner.core.resources.profile_username
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileComponent
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileState
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileUiIntent
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

private val PHOTO_SIZE = 200.dp
private val SEPARATOR_HEIGHT = 1.dp

@Composable
fun ProfileUi(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    Box(modifier) {
        ProfileUiImpl(
            state = state,
            modifier = Modifier.align(Alignment.TopCenter),
        )

        ProfileEditButton(
            onClick = { onUiIntent(ProfileUiIntent.Edit) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = AppTheme.dimensions.padding.large)
                .padding(horizontal = AppTheme.dimensions.padding.extraLarge),
        )
    }
}

@Composable
private fun ProfileUiImpl(
    state: ProfileState,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    ProfilePhoto(
        photoUrlState = state.profileUiState.photoUrl,
        modifier = Modifier
            .size(PHOTO_SIZE)
            .align(Alignment.CenterHorizontally)
            .clip(CircleShape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = CircleShape,
            )
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    Separator(Modifier.padding(horizontal = AppTheme.dimensions.padding.medium))

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    ProfileUiContent(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.extraBig)
    )
}

@Composable
private fun Separator(modifier: Modifier = Modifier) = Spacer(
    modifier
        .height(SEPARATOR_HEIGHT)
        .fillMaxWidth()
        .background(AppTheme.colors.button.primary),
)

@Composable
private fun ProfileUiContent(
    state: ProfileState,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.medium),
) {
    ProfileUiContentItem(
        title = stringResource(Res.string.profile_username),
        value = state.profileUiState.username,
    )

    ProfileUiContentItem(
        title = stringResource(Res.string.profile_name),
        value = state.profileUiState.name,
    )

    ProfileUiContentItem(
        title = stringResource(Res.string.profile_surname),
        value = state.profileUiState.surname,
    )

    ProfileUiContentItem(
        title = stringResource(Res.string.profile_email),
        value = state.profileUiState.email,
    )

    ProfileUiContentItem(
        title = stringResource(Res.string.profile_cooking_experience),
        value = "${state.profileUiState.cookingExperience} years", // TODO: figure out units
    )
}

@Composable
private fun ProfileUiContentItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) = Text(
    modifier = modifier,
    text = "$title: $value",
    color = AppTheme.colors.text.primary,
    style = AppTheme.typography.h.h2,
    fontFamily = AppTheme.typography.RalewayFontFamily,
)