package com.paranid5.cooking_corner.feature.main.profile_editor.component

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.paranid5.cooking_corner.core.common.AppDispatchers
import com.paranid5.cooking_corner.core.common.isForbidden
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.auth.getMe
import com.paranid5.cooking_corner.domain.auth.updateProfile
import com.paranid5.cooking_corner.domain.global_event.GlobalEvent.LogOut.Reason
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendLogOut
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.domain.snackbar.SnackbarMessage
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.Label
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.State
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.UiIntent
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStoreProvider.Msg
import com.paranid5.cooking_corner.feature.main.profile_editor.domain.ProfileUiState
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.toUiState
import com.paranid5.cooking_corner.utils.handleApiResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val USER_ALREADY_EXISTS = 400

internal class ProfileEditorExecutor(
    private val authRepository: AuthRepository,
    private val globalEventRepository: GlobalEventRepository,
) : CoroutineExecutor<UiIntent, Unit, State, Msg, Label>() {
    override fun executeIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.Back -> publish(Label.Back)

            is UiIntent.Save -> onSave(
                unhandledErrorSnackbar = intent.unhandledErrorSnackbar,
                userAlreadyExistsSnackbar = intent.userAlreadyExistsSnackbar,
                successSnackbar = intent.successSnackbar,
            )

            is UiIntent.UpdateProfileUiState -> dispatch(
                Msg.UpdateProfileUiState(profileUiState = intent.profileUiState)
            )

            is UiIntent.UpdateCover -> dispatch(Msg.UpdateCover(cover = intent.cover))

            is UiIntent.UpdateUsername -> dispatch(Msg.UpdateUsername(username = intent.username))

            is UiIntent.UpdateName -> dispatch(Msg.UpdateName(name = intent.name))

            is UiIntent.UpdateSurname -> dispatch(Msg.UpdateSurname(surname = intent.surname))

            is UiIntent.UpdateEmail -> dispatch(Msg.UpdateEmail(email = intent.email))

            is UiIntent.UpdateCookingExperience -> dispatch(
                Msg.UpdateCookingExperience(cookingExperience = intent.cookingExperience)
            )
        }
    }

    override fun executeAction(action: Unit) {
        dispatch(Msg.UpdateProfileUiState(UiState.Loading))

        scope.launch {
            handleApiResult(
                result = withContext(AppDispatchers.Data) {
                    authRepository.getMe()
                },
                onUnhandledError = { dispatch(Msg.UpdateProfileUiState(it.toUiState())) },
                onErrorStatusCode = { dispatch(Msg.UpdateProfileUiState(UiState.Error())) },
            ) {
                dispatch(
                    Msg.UpdateProfileUiState(
                        profileUiState = ProfileUiState.fromResponse(it).toUiState()
                    )
                )
            }
        }
    }

    private fun onSave(
        unhandledErrorSnackbar: SnackbarMessage,
        userAlreadyExistsSnackbar: SnackbarMessage,
        successSnackbar: SnackbarMessage,
    ) {
        val profileUiState = state().profileUiState.getOrNull() ?: return

        scope.launch {
            handleApiResult(
                result = withContext(AppDispatchers.Data) {
                    authRepository.updateProfile(
                        username = profileUiState.username,
                        email = profileUiState.email,
                        name = profileUiState.name,
                        surname = profileUiState.surname,
                        cookingExperienceYears = profileUiState.cookingExperience.toIntOrNull(),
                    )
                },
                onUnhandledError = { showSnackbar(unhandledErrorSnackbar) },
                onErrorStatusCode = { status ->
                    when {
                        status.isForbidden ->
                            globalEventRepository.sendLogOut(Reason.ERROR)

                        status.value == USER_ALREADY_EXISTS ->
                            showSnackbar(userAlreadyExistsSnackbar)

                        else -> showSnackbar(unhandledErrorSnackbar)
                    }
                }
            ) {
                showSnackbar(successSnackbar)
                publish(Label.Back)
            }
        }
    }

    private suspend fun showSnackbar(snackbarMessage: SnackbarMessage) =
        globalEventRepository.sendSnackbar(snackbarMessage)
}
