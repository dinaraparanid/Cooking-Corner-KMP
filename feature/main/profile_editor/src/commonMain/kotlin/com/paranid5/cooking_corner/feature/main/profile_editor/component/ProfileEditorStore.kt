package com.paranid5.cooking_corner.feature.main.profile_editor.component

import androidx.compose.runtime.Immutable
import com.arkivanov.mvikotlin.core.store.Store
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.Label
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.State
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.UiIntent
import com.paranid5.cooking_corner.feature.main.profile_editor.domain.ProfileUiState
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.getOrNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

interface ProfileEditorStore : Store<UiIntent, State, Label> {
    sealed interface UiIntent {
        data object Back : UiIntent

        data class Save(
            val unhandledErrorSnackbar: SnackbarMessage,
            val userAlreadyExistsSnackbar: SnackbarMessage,
            val successSnackbar: SnackbarMessage,
        ) : UiIntent

        data class UpdateProfileUiState(val profileUiState: UiState<ProfileUiState>) : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val profileUiState: UiState<ProfileUiState>,
        val isSurnameEmptyErrorVisible: Boolean,
    ) {
        @Transient
        val isNameEmpty = profileUiState.getOrNull()?.name?.isNotBlank() != true

        @Transient
        val isSaveButtonEnabled = isNameEmpty.not()

        constructor() : this(
            profileUiState = UiState.Undefined,
            isSurnameEmptyErrorVisible = false,
        )
    }

    sealed interface Label {
        data object Back : Label
    }
}