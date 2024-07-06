package com.paranid5.cooking_corner.featrue.auth.sign_up.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.auth_confirm_password
import com.paranid5.cooking_corner.core.resources.auth_login
import com.paranid5.cooking_corner.core.resources.auth_password
import com.paranid5.cooking_corner.core.resources.auth_password_not_confirmed
import com.paranid5.cooking_corner.core.resources.auth_password_too_short
import com.paranid5.cooking_corner.core.resources.auth_sign_up
import com.paranid5.cooking_corner.core.resources.auth_username_too_short
import com.paranid5.cooking_corner.featrue.auth.presentation.AuthConfirmButton
import com.paranid5.cooking_corner.featrue.auth.presentation.AuthEditText
import com.paranid5.cooking_corner.featrue.auth.presentation.PasswordVisibilityHandling
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpComponent
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.State
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent
import com.paranid5.cooking_corner.ui.foundation.AppBackButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpUi(
    component: SignUpComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    SignUpContent(
        state = state,
        onUiIntent = onUiIntent,
        modifier = modifier,
    )

    if (state.isErrorDialogVisible) {
        // TODO: Error dialog
    }
}

@Composable
private fun SignUpContent(
    state: State,
    onUiIntent: (UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val appPadding = AppTheme.dimensions.padding

    val passwordVisibilityHandling = PasswordVisibilityHandling(
        isPasswordVisible = state.isPasswordVisible,
        onPasswordVisibilityChanged = { onUiIntent(UiIntent.UpdatePasswordVisibility) },
    )

    ConstraintLayout(modifier = modifier) {
        val (
            backButton,
            title,
            login,
            password,
            confirmPassword,
            confirmButton,
        ) = createRefs()

        Text(
            text = stringResource(Res.string.auth_sign_up),
            color = AppTheme.colors.text.primary,
            style = AppTheme.typography.h.h1,
            fontFamily = AppTheme.typography.InterFontFamily,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = appPadding.enormous)
                centerHorizontallyTo(parent)
            },
        )

        AppBackButton(
            onClick = { onUiIntent(UiIntent.Back) },
            modifier = Modifier.constrainAs(backButton) {
                centerVerticallyTo(title)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
            },
        )

        AuthEditText(
            value = state.login,
            onValueChange = { onUiIntent(UiIntent.UpdateLoginText(login = it)) },
            placeholder = stringResource(Res.string.auth_login),
            isError = state.isUsernameShortErrorVisible,
            errorText = stringResource(Res.string.auth_username_too_short),
            modifier = Modifier.constrainAs(login) {
                top.linkTo(title.bottom, margin = appPadding.extraLarge)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            },
        )

        AuthEditText(
            value = state.password,
            onValueChange = { onUiIntent(UiIntent.UpdatePasswordText(password = it)) },
            placeholder = stringResource(Res.string.auth_password),
            passwordVisibilityHandling = passwordVisibilityHandling,
            isError = state.isPasswordShortErrorVisible,
            errorText = stringResource(Res.string.auth_password_too_short),
            modifier = Modifier.constrainAs(password) {
                top.linkTo(login.bottom, margin = appPadding.extraBig)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            },
        )

        AuthEditText(
            value = state.confirmPassword,
            onValueChange = { onUiIntent(UiIntent.UpdateConfirmPasswordText(confirmPassword = it)) },
            placeholder = stringResource(Res.string.auth_confirm_password),
            passwordVisibilityHandling = passwordVisibilityHandling,
            isError = state.isConfirmedPasswordErrorVisible,
            errorText = stringResource(Res.string.auth_password_not_confirmed),
            modifier = Modifier.constrainAs(confirmPassword) {
                top.linkTo(password.bottom, margin = appPadding.extraBig)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            },
        )

        AuthConfirmButton(
            isEnabled = state.isInputNotShort && state.isPasswordConfirmed,
            text = stringResource(Res.string.auth_sign_up),
            onClick = { onUiIntent(UiIntent.ConfirmCredentials) },
            modifier = Modifier.constrainAs(confirmButton) {
                top.linkTo(confirmPassword.bottom, margin = appPadding.large)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            },
        )
    }
}
