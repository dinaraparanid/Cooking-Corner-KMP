package com.paranid5.cooking_corner.feature.main.content.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.paranid5.cooking_corner.component.componentScope
import com.paranid5.cooking_corner.component.toStateFlow
import com.paranid5.cooking_corner.domain.global_event.GlobalEventRepository
import com.paranid5.cooking_corner.domain.global_event.sendSnackbar
import com.paranid5.cooking_corner.feature.main.generate.component.GenerateComponent
import com.paranid5.cooking_corner.feature.main.home.component.HomeComponent
import com.paranid5.cooking_corner.feature.main.profile.component.ProfileComponent
import com.paranid5.cooking_corner.feature.main.profile_editor.component.ProfileEditorComponent
import com.paranid5.cooking_corner.feature.main.recipe.component.RecipeComponent
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent.Factory.LaunchMode
import com.paranid5.cooking_corner.feature.main.search.component.SearchComponent
import com.paranid5.cooking_corner.utils.doNothing
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class MainContentComponentImpl(
    componentContext: ComponentContext,
    private val searchComponentFactory: SearchComponent.Factory,
    private val homeComponentFactory: HomeComponent.Factory,
    private val profileComponentFactory: ProfileComponent.Factory,
    private val recipeComponentFactory: RecipeComponent.Factory,
    private val generateComponentFactory: GenerateComponent.Factory,
    private val recipeEditorComponentFactory: RecipeEditorComponent.Factory,
    private val profileEditorComponentFactory: ProfileEditorComponent.Factory,
    private val globalEventRepository: GlobalEventRepository,
    private val onBack: () -> Unit,
) : MainContentComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<MainContentConfig>()

    override val stack: StateFlow<ChildStack<MainContentConfig, MainContentChild>> = childStack(
        source = navigation,
        serializer = MainContentConfig.serializer(),
        initialConfiguration = MainContentConfig.Search,
        handleBackButton = true,
        childFactory = ::createChild,
    ).toStateFlow()

    override fun onUiIntent(intent: MainContentUiIntent) {
        when (intent) {
            is MainContentUiIntent.ShowSearch -> navigation.bringToFront(MainContentConfig.Search)
            is MainContentUiIntent.ShowHome -> navigation.bringToFront(MainContentConfig.Home)
            is MainContentUiIntent.ShowProfile -> navigation.bringToFront(MainContentConfig.Profile)
        }
    }

    private fun createChild(config: MainContentConfig, componentContext: ComponentContext) =
        when (config) {
            is MainContentConfig.Home -> MainContentChild.Home(
                component = buildHomeComponent(componentContext)
            )

            is MainContentConfig.Profile -> MainContentChild.Profile(
                component = buildProfileComponent(componentContext)
            )

            is MainContentConfig.Search -> MainContentChild.Search(
                component = buildSearchComponent(componentContext)
            )

            is MainContentConfig.RecipeDetails -> MainContentChild.RecepieDetails(
                component = buildRecipeDetailsComponent(config, componentContext)
            )

            is MainContentConfig.GenerateRecipe -> MainContentChild.GenerateRecipe(
                component = buildGenerateRecipeComponent(componentContext)
            )

            is MainContentConfig.RecipeEditor -> MainContentChild.RecipeEditor(
                component = buildRecipeEditorComponent(
                    componentContext = componentContext,
                    launchMode = config.launchMode,
                )
            )

            is MainContentConfig.ProfileEditor -> MainContentChild.ProfileEditor(
                component = buildProfileEditorComponent(componentContext)
            )
        }

    private fun buildHomeComponent(componentContext: ComponentContext) =
        homeComponentFactory.create(
            componentContext = componentContext,
            onBack = { result ->
                when (result) {
                    is HomeComponent.BackResult.Dismiss ->
                        navigation.pop()

                    is HomeComponent.BackResult.ShowRecipeDetails -> navigation.bringToFront(
                        MainContentConfig.RecipeDetails(recipeId = result.recipeId)
                    )

                    is HomeComponent.BackResult.ShowAddRecipe ->
                        navigation.bringToFront(MainContentConfig.RecipeEditor(LaunchMode.New))

                    is HomeComponent.BackResult.ShowImportRecipe ->
                        navigation.bringToFront(MainContentConfig.GenerateRecipe)

                    is HomeComponent.BackResult.ShowRecipeEditor -> navigation.bringToFront(
                        MainContentConfig.RecipeEditor(LaunchMode.Edit(recipeId = result.recipeId))
                    )
                }
            },
        )

    private fun buildSearchComponent(componentContext: ComponentContext) =
        searchComponentFactory.create(
            componentContext = componentContext,
            onBack = { result ->
                when (result) {
                    is SearchComponent.BackResult.Dismiss -> navigation.pop()

                    is SearchComponent.BackResult.ShowRecipeDetails -> navigation.bringToFront(
                        MainContentConfig.RecipeDetails(recipeId = result.recipeId)
                    )
                }
            },
        )

    private fun buildProfileComponent(componentContext: ComponentContext) =
        profileComponentFactory.create(
            componentContext = componentContext,
            onBack = { result ->
                when (result) {
                    is ProfileComponent.BackResult.Dismiss -> navigation.pop()
                    is ProfileComponent.BackResult.Edit -> navigation.bringToFront(
                        MainContentConfig.ProfileEditor
                    )
                }
            }
        )

    private fun buildRecipeDetailsComponent(
        config: MainContentConfig.RecipeDetails,
        componentContext: ComponentContext,
    ) = recipeComponentFactory.create(
        componentContext = componentContext,
        recipeId = config.recipeId,
        onBack = { result ->
            when (result) {
                is RecipeComponent.BackResult.Dismiss -> navigation.pop()

                is RecipeComponent.BackResult.Edit -> navigation.bringToFront(
                    MainContentConfig.RecipeEditor(LaunchMode.Edit(recipeId = result.recipeId))
                )
            }
        },
    )

    private fun buildGenerateRecipeComponent(componentContext: ComponentContext) =
        generateComponentFactory.create(
            componentContext = componentContext,
            onBack = { result ->
                when (result) {
                    is GenerateComponent.BackResult.Dismiss -> navigation.pop()

                    is GenerateComponent.BackResult.Generated -> navigation.replaceCurrent(
                        MainContentConfig.RecipeEditor(
                            LaunchMode.Generate(recipeParamsUiState = result.params)
                        )
                    )
                }
            }
        )

    private fun buildRecipeEditorComponent(
        componentContext: ComponentContext,
        launchMode: LaunchMode,
    ) = recipeEditorComponentFactory.create(
        componentContext = componentContext,
        launchMode = launchMode,
        onBack = { result ->
            when (result) {
                is RecipeEditorComponent.BackResult.Dismiss -> doNothing
                is RecipeEditorComponent.BackResult.Uploaded -> componentScope.launch {
                    globalEventRepository.sendSnackbar(result.snackbarMessage)
                }
            }

            navigation.pop()
        }
    )

    private fun buildProfileEditorComponent(componentContext: ComponentContext) =
        profileEditorComponentFactory.create(
            componentContext = componentContext,
            onBack = { result ->
                when (result) {
                    is ProfileEditorComponent.BackResult.Dismiss -> doNothing
                    is ProfileEditorComponent.BackResult.Updated -> componentScope.launch {
                        globalEventRepository.sendSnackbar(result.snackbarMessage)
                    }
                }

                navigation.pop()
            }
        )

    class Factory(
        private val searchComponentFactory: SearchComponent.Factory,
        private val homeComponentFactory: HomeComponent.Factory,
        private val profileComponentFactory: ProfileComponent.Factory,
        private val recipeComponentFactory: RecipeComponent.Factory,
        private val generateComponentFactory: GenerateComponent.Factory,
        private val recipeEditorComponentFactory: RecipeEditorComponent.Factory,
        private val profileEditorComponentFactory: ProfileEditorComponent.Factory,
        private val globalEventRepository: GlobalEventRepository,
    ) : MainContentComponent.Factory {
        override fun create(
            componentContext: ComponentContext,
            onBack: () -> Unit,
        ): MainContentComponent = MainContentComponentImpl(
            componentContext = componentContext,
            searchComponentFactory = searchComponentFactory,
            homeComponentFactory = homeComponentFactory,
            profileComponentFactory = profileComponentFactory,
            recipeComponentFactory = recipeComponentFactory,
            generateComponentFactory = generateComponentFactory,
            recipeEditorComponentFactory = recipeEditorComponentFactory,
            profileEditorComponentFactory = profileEditorComponentFactory,
            globalEventRepository = globalEventRepository,
            onBack = onBack,
        )
    }
}