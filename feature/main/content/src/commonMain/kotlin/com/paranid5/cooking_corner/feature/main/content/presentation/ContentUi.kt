package com.paranid5.cooking_corner.feature.main.content.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.paranid5.cooking_corner.feature.main.content.component.MainContentChild
import com.paranid5.cooking_corner.feature.main.generate.presentation.GenerateUi
import com.paranid5.cooking_corner.feature.main.home.presentation.HomeUi
import com.paranid5.cooking_corner.feature.main.profile.presentation.ProfileUi
import com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed.RecipeDetailsUi
import com.paranid5.cooking_corner.feature.main.recipe_editor.presentation.RecipeEditorUi
import com.paranid5.cooking_corner.feature.main.search.presentation.SearchUi
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
internal fun ContentUi(
    childStack: State<ChildStack<*, MainContentChild>>,
    modifier: Modifier = Modifier,
) {
    val stack by childStack

    Children(
        stack = stack,
        animation = stackAnimation(fade()),
        modifier = modifier,
    ) {
        val screenModifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background.primary)

        when (val child = it.instance) {
            is MainContentChild.Home -> HomeUi(
                component = child.component,
                modifier = screenModifier,
            )

            is MainContentChild.Profile -> ProfileUi(
                component = child.component,
                modifier = screenModifier,
            )

            is MainContentChild.Search -> SearchUi(
                component = child.component,
                modifier = screenModifier,
            )

            is MainContentChild.RecepieDetails -> RecipeDetailsUi(
                component = child.component,
                modifier = screenModifier,
            )

            is MainContentChild.AddRecipe -> RecipeEditorUi(
                component = child.component,
                modifier = screenModifier,
            )

            is MainContentChild.GenerateRecipe -> GenerateUi(
                component = child.component,
                modifier = screenModifier,
            )

            is MainContentChild.RecipeEditor -> RecipeEditorUi(
                component = child.component,
                modifier = screenModifier,
            )
        }
    }
}