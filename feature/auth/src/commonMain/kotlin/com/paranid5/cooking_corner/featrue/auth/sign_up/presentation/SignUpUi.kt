package com.paranid5.cooking_corner.featrue.auth.sign_up.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.confirm_password
import com.paranid5.cooking_corner.core.resources.login
import com.paranid5.cooking_corner.core.resources.password
import com.paranid5.cooking_corner.core.resources.sign_up
import com.paranid5.cooking_corner.featrue.auth.presentation.AuthConfirmButton
import com.paranid5.cooking_corner.featrue.auth.presentation.AuthEditText
import com.paranid5.cooking_corner.featrue.auth.presentation.PASSWORD_MASK
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpComponent
import com.paranid5.cooking_corner.featrue.auth.sign_up.component.SignUpStore.UiIntent
import com.paranid5.cooking_corner.ui.common.AppBackButton
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SignUpUi(
    component: SignUpComponent,
    modifier: Modifier = Modifier,
) {
    val appPadding = AppTheme.dimensions.padding
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    ConstraintLayout(modifier = modifier) {
        val (
            backButton,
            title,
            login,
            password,
            confirmPassword,
            confirmButton
        ) = createRefs()

        Text(
            text = stringResource(Res.string.sign_up),
            color = AppTheme.colors.text.primary,
            style = AppTheme.typography.h.h1,
            fontFamily = AppTheme.typography.InterFontFamily,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = appPadding.enormous)
                centerHorizontallyTo(parent)
            }
        )

        AppBackButton(
            onBack = { onUiIntent(UiIntent.Back) },
            modifier = Modifier.constrainAs(backButton) {
                centerVerticallyTo(title)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
            },
        )

        AuthEditText(
            value = state.login,
            onValueChange = { onUiIntent(UiIntent.UpdateLoginText(login = it)) },
            placeholder = stringResource(Res.string.login),
            modifier = Modifier.constrainAs(login) {
                top.linkTo(title.bottom, margin = appPadding.extraLarge)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            }
        )

        AuthEditText(
            value = state.password,
            onValueChange = { onUiIntent(UiIntent.UpdatePasswordText(password = it)) },
            placeholder = stringResource(Res.string.password),
            visualTransformation = PasswordVisualTransformation(mask = PASSWORD_MASK),
            modifier = Modifier.constrainAs(password) {
                top.linkTo(login.bottom, margin = appPadding.extraBig)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            }
        )

        AuthEditText(
            value = state.confirmPassword,
            onValueChange = { onUiIntent(UiIntent.UpdateConfirmPasswordText(confirmPassword = it)) },
            placeholder = stringResource(Res.string.confirm_password),
            visualTransformation = PasswordVisualTransformation(mask = PASSWORD_MASK),
            modifier = Modifier.constrainAs(confirmPassword) {
                top.linkTo(password.bottom, margin = appPadding.extraBig)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            }
        )

        AuthConfirmButton(
            text = stringResource(Res.string.sign_up),
            onClick = { onUiIntent(UiIntent.ConfirmCredentials) },
            modifier = Modifier.constrainAs(confirmButton) {
                top.linkTo(confirmPassword.bottom, margin = appPadding.large)
                start.linkTo(parent.start, margin = appPadding.extraMedium)
                end.linkTo(parent.end, margin = appPadding.extraMedium)
                width = Dimension.fillToConstraints
            }
        )
    }
}