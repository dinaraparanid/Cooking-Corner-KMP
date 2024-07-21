package com.paranid5.cooking_corner.feature.main.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_edit
import com.paranid5.cooking_corner.core.resources.profile_cooking_experience
import com.paranid5.cooking_corner.core.resources.profile_cooking_experience_value
import com.paranid5.cooking_corner.core.resources.profile_edit
import com.paranid5.cooking_corner.core.resources.profile_email
import com.paranid5.cooking_corner.core.resources.profile_error
import com.paranid5.cooking_corner.core.resources.profile_logout
import com.paranid5.cooking_corner.core.resources.profile_name
import com.paranid5.cooking_corner.core.resources.profile_placeholder_unknown
import com.paranid5.cooking_corner.core.resources.profile_surname
import com.paranid5.cooking_corner.core.resources.profile_username
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileComponent
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileState
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileUiIntent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.entity.data
import com.paranid5.cooking_corner.ui.entity.profile.ProfileUiState
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.AppProgressIndicator
import com.paranid5.cooking_corner.ui.foundation.AppPullRefreshIndicator
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppErrorStub
import com.paranid5.cooking_corner.ui.foundation.rememberPullRefreshWithDuration
import com.paranid5.cooking_corner.ui.getOrNull
import com.paranid5.cooking_corner.ui.isLoadingOrRefreshing
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

private val PHOTO_SIZE = 200.dp
private val SEPARATOR_HEIGHT = 1.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileUi(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.stateFlow.collectAsState()
    val onUiIntent = component::onUiIntent

    val (pullRefreshState, isRefreshingShown) = rememberPullRefreshWithDuration(
        isRefreshing = state.uiState.isLoadingOrRefreshing,
        onRefresh = { onUiIntent(ProfileUiIntent.Refresh) },
    )

    @Composable
    fun ColumnScope.buildProfileButtonsModifier() =
        Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .padding(vertical = AppTheme.dimensions.padding.large)
            .padding(horizontal = AppTheme.dimensions.padding.extraLarge)

    @Composable
    fun ColumnScope.Content(profileUiState: ProfileUiState) {
        ProfileUiImpl(
            state = state,
            profileUiState = profileUiState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = AppTheme.dimensions.padding.medium),
        )

        Spacer(Modifier.weight(1F))

        ProfileButtons(
            isProfileEditable = true,
            onUiIntent = onUiIntent,
            modifier = buildProfileButtonsModifier(),
        )
    }

    @Composable
    fun ColumnScope.Error() {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            AppErrorStub(
                errorMessage = stringResource(Res.string.profile_error),
                modifier = Modifier.align(Alignment.Center),
            )
        }

        ProfileButtons(
            isProfileEditable = false,
            onUiIntent = onUiIntent,
            modifier = buildProfileButtonsModifier(),
        )
    }

    Column(
        modifier
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState())
    ) {
        AppPullRefreshIndicator(
            isRefreshing = isRefreshingShown,
            state = pullRefreshState,
            modifier = Modifier
                .zIndex(1F)
                .align(Alignment.CenterHorizontally),
        )

        when (state.uiState) {
            is UiState.Data, is UiState.Refreshing, is UiState.Success ->
                state.uiState.getOrNull()?.let { Content(profileUiState = it) }

            is UiState.Error -> Error()

            is UiState.Loading, is UiState.Undefined ->
                Box(Modifier.fillMaxSize()) {
                    AppProgressIndicator(Modifier.align(Alignment.Center))
                }
        }
    }
}

@Composable
private fun ProfileUiImpl(
    state: ProfileState,
    profileUiState: ProfileUiState,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    ProfileCover(
        cover = profileUiState.cover?.data,
        modifier = Modifier
            .size(PHOTO_SIZE)
            .align(Alignment.CenterHorizontally)
            .clip(CircleShape)
            .border(
                width = AppTheme.dimensions.borders.minimum,
                color = AppTheme.colors.button.primary,
                shape = CircleShape,
            )
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    Separator(Modifier.padding(horizontal = AppTheme.dimensions.padding.medium))

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    ProfileUiContent(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimensions.padding.extraBig)
    )
}

@Composable
private fun ProfileButtons(
    isProfileEditable: Boolean,
    onUiIntent: (ProfileUiIntent) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    if (isProfileEditable) {
        ProfileButton(
            text = stringResource(Res.string.profile_edit),
            icon = vectorResource(Res.drawable.ic_edit),
            onClick = { onUiIntent(ProfileUiIntent.Edit) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(AppTheme.dimensions.padding.large))
    }

    ProfileButton(
        text = stringResource(Res.string.profile_logout),
        onClick = { onUiIntent(ProfileUiIntent.LogOut) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.button.secondaryDarker,
            disabledContainerColor = AppTheme.colors.button.secondaryDarker,
        )
    )
}

@Composable
private fun Separator(modifier: Modifier = Modifier) = Spacer(
    modifier
        .height(SEPARATOR_HEIGHT)
        .fillMaxWidth()
        .background(AppTheme.colors.button.primary),
)

@Composable
private fun ProfileUiContent(
    state: ProfileState,
    modifier: Modifier = Modifier,
) {
    val profileUiState = state.uiState.getOrNull() ?: return
    val placeholderUnknown = stringResource(Res.string.profile_placeholder_unknown)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.medium),
    ) {
        ProfileUiContentItem(
            title = stringResource(Res.string.profile_username),
            value = profileUiState.username,
        )

        ProfileUiContentItem(
            title = stringResource(Res.string.profile_name),
            value = profileUiState.name?.takeIf(String::isNotBlank) ?: placeholderUnknown,
        )

        ProfileUiContentItem(
            title = stringResource(Res.string.profile_surname),
            value = profileUiState.surname?.takeIf(String::isNotBlank) ?: placeholderUnknown,
        )

        ProfileUiContentItem(
            title = stringResource(Res.string.profile_email),
            value = profileUiState.email?.takeIf(String::isNotBlank) ?: placeholderUnknown,
        )

        ProfileUiContentItem(
            title = stringResource(Res.string.profile_cooking_experience),
            value = profileUiState.cookingExperienceYears?.let {
                stringResource(Res.string.profile_cooking_experience_value, it)
            } ?: placeholderUnknown,
        )
    }
}

@Composable
private fun ProfileUiContentItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) = AppMainText(
    modifier = modifier,
    text = "$title: $value",
    style = AppTheme.typography.h.h2,
)
