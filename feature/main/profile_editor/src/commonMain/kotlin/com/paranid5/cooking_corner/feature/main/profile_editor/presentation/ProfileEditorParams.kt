package com.paranid5.cooking_corner.feature.main.profile_editor.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore
import com.paranid5.cooking_corner.feature.main.profile_editor.domain.ProfileUiState

@Composable
internal fun ProfileEditorParams(
    state: ProfileEditorStore.State,
    profileUiState: ProfileUiState,
    onUiIntent: (ProfileEditorStore.UiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {

}