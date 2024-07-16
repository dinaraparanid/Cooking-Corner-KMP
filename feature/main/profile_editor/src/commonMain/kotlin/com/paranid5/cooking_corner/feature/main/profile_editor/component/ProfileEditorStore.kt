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

        data class UpdateCover(val cover: ByteArray?) : UiIntent

        data class UpdateUsername(val username: String) : UiIntent

        data class UpdateName(val name: String) : UiIntent

        data class UpdateSurname(val surname: String) : UiIntent

        data class UpdateEmail(val email: String) : UiIntent

        data class UpdateCookingExperience(val cookingExperience: String) : UiIntent
    }

    @Serializable
    @Immutable
    data class State(
        val profileUiState: UiState<ProfileUiState>,
        val isSurnameEmptyErrorVisible: Boolean,
    ) {
        companion object {
            private const val USERNAME_MIN_INPUT_LENGTH = 6
        }

        @Transient
        val isUsernameShort = profileUiState.getOrNull()?.let {
            it.username.length >= USERNAME_MIN_INPUT_LENGTH
        } != true

        @Transient
        val isSaveButtonEnabled = isUsernameShort.not()

        constructor() : this(
            profileUiState = UiState.Undefined,
            isSurnameEmptyErrorVisible = false,
        )
    }

    sealed interface Label {
        data object Back : Label
    }
}
