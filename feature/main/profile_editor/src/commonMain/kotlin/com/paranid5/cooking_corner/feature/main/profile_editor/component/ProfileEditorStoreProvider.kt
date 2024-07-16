package com.paranid5.cooking_corner.feature.main.profile_editor.component

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.Label
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.State
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStore.UiIntent
import com.paranid5.cooking_corner.feature.main.profile_editor.domain.ProfileUiState
import com.paranid5.cooking_corner.ui.UiState

internal class ProfileEditorStoreProvider(
    private val storeFactory: StoreFactory,
    private val authRepository: AuthRepository,
    private val globalEventRepository: GlobalEventRepository,
) {
    sealed interface Msg {
        data class UpdateProfileUiState(val profileUiState: UiState<ProfileUiState>) : Msg
    }

    fun provide(initialState: State): ProfileEditorStore = object :
        ProfileEditorStore,
        Store<UiIntent, State, Label> by storeFactory.create(
            name = "ProfileEditorStore",
            initialState = initialState,
            executorFactory = {
                ProfileEditorExecutor(
                    authRepository = authRepository,
                    globalEventRepository = globalEventRepository,
                )
            },
            reducer = ProfileEditorReducer,
            bootstrapper = SimpleBootstrapper(Unit),
        ) {}

    class Factory(
        private val storeFactory: StoreFactory,
        private val authRepository: AuthRepository,
        private val globalEventRepository: GlobalEventRepository,
    ) {
        fun create() = ProfileEditorStoreProvider(
            storeFactory = storeFactory,
            authRepository = authRepository,
            globalEventRepository = globalEventRepository,
        )
    }
}
