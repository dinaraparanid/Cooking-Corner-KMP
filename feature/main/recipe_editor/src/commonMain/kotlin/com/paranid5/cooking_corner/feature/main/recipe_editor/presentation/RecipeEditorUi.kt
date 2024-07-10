package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.feature.main.recipe_editor.component.RecipeEditorComponent
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun RecipeEditorUi(
    component: RecipeEditorComponent,
    modifier: Modifier = Modifier,
) {
    Box(modifier.background(AppTheme.colors.background.primary)) {
        AppMainText(
            text = "TODO: RecipeEditorUi",
            style = AppTheme.typography.h.h1,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}