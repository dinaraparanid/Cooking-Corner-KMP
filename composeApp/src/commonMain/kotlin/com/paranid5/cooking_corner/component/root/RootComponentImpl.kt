package com.paranid5.cooking_corner.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.paranid5.cooking_corner.component.componentScope
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.domain.auth.AuthRepository
import com.paranid5.cooking_corner.featrue.auth.component.AuthComponent
import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponent
import com.paranid5.cooking_corner.feature.main.root.component.MainRootComponent.AuthorizeType
import com.paranid5.cooking_corner.feature.splash.component.SplashScreenComponent
import com.paranid5.cooking_corner.ui.UiState
import com.paranid5.cooking_corner.ui.isOk
import com.paranid5.cooking_corner.utils.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

internal class RootComponentImpl(
    componentContext: ComponentContext,
    private val authRepository: AuthRepository,
    private val splashScreenComponentFactory: SplashScreenComponent.Factory,
    private val authComponentFactory: AuthComponent.Factory,
    private val mainRootComponentFactory: MainRootComponent.Factory,
) : RootComponent,
    ComponentContext by componentContext {
    @Serializable
    sealed interface Config {
        @Serializable
        data object SplashScreen : Config

        @Serializable
        data object Auth : Config

        @Serializable
        data class Main(val authType: AuthorizeType) : Config
    }

    private val navigation = StackNavigation<Config>()

    override val stack: StateFlow<ChildStack<Config, RootChild>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.SplashScreen,
        handleBackButton = true,
        childFactory = ::createChild,
    ).toStateFlow()

    private val _stateFlow = MutableStateFlow(RootState())
    override val stateFlow = _stateFlow.asStateFlow()

    init {
        doOnCreate {
            componentScope.launch {
                val authState = checkAuthorized()
                _stateFlow.updateState { copy(isAuthorizedUiState = authState) }
            }
        }
    }

    private suspend fun checkAuthorized(): UiState<Unit> {
        val accessToken = authRepository.accessTokenFlow.firstOrNull()
        val refreshToken = authRepository.refreshTokenFlow.firstOrNull()

        return when {
            accessToken == null || refreshToken == null -> UiState.Error()

            else -> authRepository
                .verifyToken(accessToken)
                .fold(
                    ifLeft = { UiState.Error() },
                    ifRight = { res ->
                        res.fold(ifLeft = { UiState.Error() }, ifRight = { UiState.Success })
                    }
                )
        }
    }

    private fun createChild(config: Config, componentContext: ComponentContext) =
        when (config) {
            is Config.SplashScreen -> RootChild.SplashScreen(
                buildSplashScreenComponent(componentContext)
            )

            is Config.Auth -> RootChild.Auth(
                buildAuthComponent(componentContext)
            )

            is Config.Main -> RootChild.Main(
                buildMainComponent(
                    config = config,
                    componentContext = componentContext,
                )
            )
        }

    private fun buildSplashScreenComponent(componentContext: ComponentContext) =
        splashScreenComponentFactory.create(
            componentContext = componentContext,
            onSplashScreenClosed = {
                navigation.replaceCurrent(
                    when {
                        stateFlow.value.isAuthorizedUiState.isOk ->
                            Config.Main(AuthorizeType.SIGNED_IN)

                        else -> Config.Auth
                    }
                )
            }
        )

    private fun buildAuthComponent(componentContext: ComponentContext) =
        authComponentFactory.create(
            componentContext = componentContext,
            onBack = { result ->
                when (result) {
                    is AuthComponent.BackResult.Dismiss ->
                        navigation.pop()

                    is AuthComponent.BackResult.SignedIn ->
                        navigation.replaceCurrent(Config.Main(AuthorizeType.SIGNED_IN))

                    is AuthComponent.BackResult.SignedUp ->
                        navigation.replaceCurrent(Config.Main(AuthorizeType.SIGNED_UP))
                }
            },
        )

    private fun buildMainComponent(
        config: Config.Main,
        componentContext: ComponentContext,
    ) = mainRootComponentFactory.create(
        componentContext = componentContext,
        authType = config.authType,
        onBack = navigation::pop,
    )

    internal class Factory(
        private val authRepository: AuthRepository,
        private val splashScreenComponentFactory: SplashScreenComponent.Factory,
        private val authComponentFactory: AuthComponent.Factory,
        private val mainRootComponentFactory: MainRootComponent.Factory,
    ) : RootComponent.Factory {
        override fun create(componentContext: ComponentContext): RootComponent =
            RootComponentImpl(
                componentContext = componentContext,
                authRepository = authRepository,
                splashScreenComponentFactory = splashScreenComponentFactory,
                authComponentFactory = authComponentFactory,
                mainRootComponentFactory = mainRootComponentFactory,
            )
    }
}