package com.paranid5.cooking_corner.feature.main.profile_editor.component

import com.arkivanov.mvikotlin.core.store.Reducer
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.State
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStoreProvider.Msg
import com.paranid5.cooking_corner.ui.entity.ImageContainer
import com.paranid5.cooking_corner.ui.entity.profile.ProfileUiState
import com.paranid5.cooking_corner.ui.udpateData

internal object ProfileEditorReducer : Reducer<State, Msg> {
    override fun State.reduce(msg: Msg): State = when (msg) {
        is Msg.UpdateProfileUiState -> copy(profileUiState = msg.profileUiState)

        is Msg.UpdateCover -> updateProfileUiState { copy(cover = ImageContainer.Bytes(msg.cover)) }

        is Msg.UpdateUsername -> updateProfileUiState { copy(username = msg.username) }

        is Msg.UpdateName -> updateProfileUiState { copy(name = msg.name) }

        is Msg.UpdateSurname -> updateProfileUiState { copy(surname = msg.surname) }

        is Msg.UpdateEmail -> updateProfileUiState { copy(email = msg.email) }

        is Msg.UpdateCookingExperience -> updateProfileUiState {
            copy(cookingExperience = msg.cookingExperience)
        }
    }

    private inline fun State.updateProfileUiState(func: ProfileUiState.() -> ProfileUiState) =
        copy(profileUiState = profileUiState.udpateData(func))
}