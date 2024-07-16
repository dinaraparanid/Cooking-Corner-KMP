package com.paranid5.cooking_corner.feature.main.profile_editor.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.State
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStoreProvider.Msg
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.udpateData

internal object ProfileEditorReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg): State = when (msg) {
        is Msg.UpdateProfileUiState -> copy(profileUiState = msg.profileUiState)

        is Msg.UpdateCover -> copy(
            profileUiState = profileUiState.udpateData {
                copy(cover = ImageContainer.Bytes(msg.cover))
            }
        )
    }
}