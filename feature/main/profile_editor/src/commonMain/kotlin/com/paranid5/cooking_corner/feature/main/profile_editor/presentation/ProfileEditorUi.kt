package com.paranid5.cooking_corner.feature.main.profile_editor.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.auth_user_already_exists
import com.paranid5.cooking_corner.core.resources.profile_error
import com.paranid5.cooking_corner.core.resources.profile_updated
import com.paranid5.cooking_corner.core.resources.something_went_wrong
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.domain.snackbar.SnackbarType
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorComponent
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.State
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.UiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.profile.ProfileUiState
import com.paranid5.cooking_corner.ui.foundation.AppOutlinedBackButton
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppErrorStub
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.simpleShadow
import com.paranid5.cooking_corner.utils.doNothing
import org.jetbrains.compose.resources.stringResource

private val BACK_BUTTON_SIZE = 36.dp

@Composable
fun ProfileEditorUi(
    component: ProfileEditorComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    Box(modifier) {
        when (state.profileUiState) {
            is UiState.Data -> state.profileUiState.getOrNull()?.let {
                ProfileEditorUiContent(
                    state = state,
                    profileUiState = it,
                    onUiIntent = onUiIntent,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is UiState.Error -> AppErrorStub(
                errorMessage = stringResource(Res.string.profile_error),
                modifier = Modifier.fillMaxSize(),
            )

            is UiState.Undefined, UiState.Loading, is UiState.Refreshing ->
                AppProgressIndicator(Modifier.align(Alignment.Center))

            is UiState.Success -> doNothing
        }
    }
}

@Composable
private fun ProfileEditorUiContent(
    state: State,
    profileUiState: ProfileUiState,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = ConstraintLayout(modifier.verticalScroll(rememberScrollState())) {
    val appPadding = AppTheme.dimensions.padding

    val unhandledErrorSnackbar = UnhandledErrorSnackbar()
    val userAlreadyExistsSnackbar = UserAlreadyExistsSnackbar()
    val successSnackbar = SuccessSnackbar()

    val (
        backButton,
        cover,
        divider,
        saveButton,
        params,
    ) = createRefs()

    AppOutlinedBackButton(
        modifier = Modifier
            .size(BACK_BUTTON_SIZE)
            .simpleShadow()
            .background(AppTheme.colors.background.primary)
            .constrainAs(backButton) {
                top.linkTo(parent.top, margin = appPadding.medium)
                start.linkTo(parent.start, margin = appPadding.medium)
            }
    ) { onUiIntent(UiIntent.Back) }

    ProfileCoverWithPicker(
        cover = profileUiState.cover,
        modifier = Modifier.constrainAs(cover) {
            top.linkTo(parent.top, margin = appPadding.medium)
            centerHorizontallyTo(parent)
        },
    ) { onUiIntent(UiIntent.UpdateCover(it)) }

    Divider(
        color = AppTheme.colors.button.primary,
        modifier = Modifier.constrainAs(divider) {
            top.linkTo(cover.bottom, margin = appPadding.medium)
            start.linkTo(parent.start, margin = appPadding.medium)
            end.linkTo(parent.end, margin = appPadding.medium)
            width = Dimension.fillToConstraints
        }
    )

    ProfileEditorSaveButton(
        modifier = Modifier.constrainAs(saveButton) {
            top.linkTo(divider.bottom, margin = appPadding.medium)
            start.linkTo(parent.start, margin = appPadding.medium)
            end.linkTo(parent.end, margin = appPadding.medium)
            width = Dimension.fillToConstraints
        },
    ) {
        onUiIntent(
            UiIntent.Save(
                unhandledErrorSnackbar = unhandledErrorSnackbar,
                userAlreadyExistsSnackbar = userAlreadyExistsSnackbar,
                successSnackbar = successSnackbar,
            )
        )
    }

    ProfileEditorParams(
        state = state,
        profileUiState = profileUiState,
        onUiIntent = onUiIntent,
        modifier = Modifier.constrainAs(params) {
            top.linkTo(saveButton.bottom, margin = appPadding.medium)
            bottom.linkTo(parent.bottom, margin = appPadding.medium)
            start.linkTo(parent.start, margin = appPadding.medium)
            end.linkTo(parent.end, margin = appPadding.medium)
            width = Dimension.fillToConstraints
        }
    )
}

@Composable
private fun UnhandledErrorSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.something_went_wrong),
    snackbarType = SnackbarType.NEGATIVE,
)

@Composable
private fun UserAlreadyExistsSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.auth_user_already_exists),
    snackbarType = SnackbarType.NEGATIVE,
)

@Composable
private fun SuccessSnackbar() = SnackbarMessage(
    message = stringResource(Res.string.profile_updated),
    snackbarType = SnackbarType.POSITIVE,
)
