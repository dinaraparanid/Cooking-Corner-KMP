package com.paranid5.cooking_corner.feature.main.profile_editor.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.auth_username_too_short
import com.paranid5.cooking_corner.core.resources.profile_cooking_experience
import com.paranid5.cooking_corner.core.resources.profile_email
import com.paranid5.cooking_corner.core.resources.profile_name
import com.paranid5.cooking_corner.core.resources.profile_surname
import com.paranid5.cooking_corner.core.resources.profile_username
import com.paranid5.cooking_corner.core.resources.unit_years
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.State
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.UiIntent
import com.paranid5.cooking_corner.feature.main.profile_editor.domain.ProfileUiState
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

private const val COOKING_EXPERIENCE_INPUT_MAX_LENGTH = 2

@Composable
internal fun ProfileEditorParams(
    state: State,
    profileUiState: ProfileUiState,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.medium),
) {
    ProfileEditorTextField(
        value = profileUiState.username,
        placeholder = stringResource(Res.string.profile_username),
        isError = state.isUsernameShort,
        errorMessage = stringResource(Res.string.auth_username_too_short),
        onValueChange = { onUiIntent(UiIntent.UpdateUsername(username = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    ProfileEditorTextField(
        value = profileUiState.name,
        placeholder = stringResource(Res.string.profile_name),
        onValueChange = { onUiIntent(UiIntent.UpdateName(name = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    ProfileEditorTextField(
        value = profileUiState.surname,
        placeholder = stringResource(Res.string.profile_surname),
        onValueChange = { onUiIntent(UiIntent.UpdateSurname(surname = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    ProfileEditorTextField(
        value = profileUiState.email,
        placeholder = stringResource(Res.string.profile_email),
        onValueChange = { onUiIntent(UiIntent.UpdateEmail(email = it)) },
        modifier = Modifier.fillMaxWidth(),
    )

    ProfileEditorTextField(
        modifier = Modifier.fillMaxWidth(),
        value = profileUiState.cookingExperience,
        placeholder = stringResource(Res.string.profile_cooking_experience),
        suffix = stringResource(Res.string.unit_years),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { input ->
            onUiIntent(
                UiIntent.UpdateCookingExperience(
                    cookingExperience = input.take(COOKING_EXPERIENCE_INPUT_MAX_LENGTH),
                )
            )
        },
    )
}
