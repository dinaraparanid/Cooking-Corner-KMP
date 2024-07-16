package com.paranid5.cooking_corner.feature.main.profile_editor.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorComponent
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun ProfileEditorUi(
    component: ProfileEditorComponent,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        AppMainText(
            text = "TODO: ProfileEditorUi",
            style = AppTheme.typography.h.h1,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
