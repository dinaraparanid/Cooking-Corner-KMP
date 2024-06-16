package com.paranid5.cooking_corner.featrue.auth.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.paranid5.cooking_corner.featrue.auth.component.AuthChild
import com.paranid5.cooking_corner.featrue.auth.component.AuthComponent
import com.paranid5.cooking_corner.featrue.auth.sign_in.presentation.SignInUi

@Composable
fun AuthUi(
    component: AuthComponent,
    modifier: Modifier = Modifier,
) {
    val childStack by component.stack.collectAsState()

    Children(
        stack = childStack,
        modifier = modifier,
    ) { child ->
        when (val instance = child.instance) {
            is AuthChild.SignIn -> SignInUi(
                component = instance.component,
                modifier = Modifier.fillMaxSize(),
            )

            is AuthChild.SignUp -> Text("TODO: Sign Up")
        }
    }
}