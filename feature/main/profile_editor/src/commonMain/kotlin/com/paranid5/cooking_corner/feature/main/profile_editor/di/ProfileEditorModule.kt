package com.paranid5.cooking_corner.feature.main.profile_editor.di

import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorComponent
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorComponentImpl
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorStoreProvider
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.new

val profileEditorModule = DI.Module("profileEditorModule") {
    bind<ProfileEditorComponent.Factory>() with multiton { new(ProfileEditorComponentImpl::Factory) }
    bind<ProfileEditorStoreProvider.Factory>() with multiton { new(ProfileEditorStoreProvider::Factory) }
}
