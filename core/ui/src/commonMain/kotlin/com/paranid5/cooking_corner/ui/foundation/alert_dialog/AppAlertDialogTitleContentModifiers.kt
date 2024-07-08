package com.paranid5.cooking_corner.ui.foundation.alert_dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.ui.utils.applyIf

class AppAlertDialogTitleContentModifiers private constructor(
    val titleModifier: Modifier,
    val contentModifier: Modifier,
) {
    companion object {
        @Composable
        fun create(
            titleModifier: Modifier = Modifier
                .padding(top = AppTheme.dimensions.padding.extraLarge)
                .padding(horizontal = AppTheme.dimensions.padding.extraLarge),
            contentModifier: Modifier = Modifier
                .padding(top = AppTheme.dimensions.padding.extraMedium)
                .padding(horizontal = AppTheme.dimensions.padding.extraLarge),
        ) = AppAlertDialogTitleContentModifiers(
            titleModifier = titleModifier,
            contentModifier = contentModifier,
        )

        @Composable
        fun default(
            hasTitle: Boolean,
            initialTitleModifier: Modifier = Modifier,
            initialContentModifier: Modifier = Modifier,
        ) = AppAlertDialogTitleContentModifiers(
            titleModifier = initialTitleModifier.applyIf(hasTitle) {
                padding(top = AppTheme.dimensions.padding.extraLarge)
                    .padding(horizontal = AppTheme.dimensions.padding.extraLarge)
            },
            contentModifier = initialContentModifier
                .padding(horizontal = AppTheme.dimensions.padding.extraLarge)
                .padding(
                    top = if (hasTitle) {
                        AppTheme.dimensions.padding.extraMedium
                    } else {
                        AppTheme.dimensions.padding.extraLarge
                    }
                ),
        )
    }
}