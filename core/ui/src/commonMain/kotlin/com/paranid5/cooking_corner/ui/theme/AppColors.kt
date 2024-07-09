package com.paranid5.cooking_corner.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.paranid5.cooking_corner.ui.BackgroundSnackbarNegative
import com.paranid5.cooking_corner.ui.BackgroundSnackbarNeutral
import com.paranid5.cooking_corner.ui.BackgroundSnackbarPositive
import com.paranid5.cooking_corner.ui.DarkerPastel
import com.paranid5.cooking_corner.ui.DarkestPastel
import com.paranid5.cooking_corner.ui.Error
import com.paranid5.cooking_corner.ui.MainBlack
import com.paranid5.cooking_corner.ui.MainPastel
import com.paranid5.cooking_corner.ui.MainWhite
import com.paranid5.cooking_corner.ui.OrangeSelect
import com.paranid5.cooking_corner.ui.SecondaryBlack
import com.paranid5.cooking_corner.ui.TransparentUtilityDark

@Immutable
data class AppColors(
    val colorScheme: ColorScheme,
    val error: Color,
    val background: AppBackgroundColors,
    val text: AppTextColors,
    val button: AppButtonColors,
    val snackbar: AppSnackbarColors,
) {
    companion object {
        private val ColorScheme = darkColorScheme(
            primary = MainPastel,
            secondary = MainBlack,
            tertiary = MainBlack,
            background = MainPastel,
            onBackground = MainBlack,
            onSurface = TransparentUtilityDark,
        )

        internal fun create() = AppColors(
            colorScheme = ColorScheme,
            error = Error,
            background = AppBackgroundColors.default,
            text = AppTextColors.default,
            button = AppButtonColors.default,
            snackbar = AppSnackbarColors.default,
        )
    }

    fun getTabColor(isCurrent: Boolean) = if (isCurrent) OrangeSelect else MainBlack

    val orange = OrangeSelect
}

@Immutable
data class AppBackgroundColors(
    val primary: Color,
    val primaryDarker: Color,
    val primaryDarkest: Color,
    val alternative: Color,
) {
    companion object {
        internal val default = AppBackgroundColors(
            primary = MainPastel,
            primaryDarker = DarkerPastel,
            primaryDarkest = DarkestPastel,
            alternative = MainWhite,
        )
    }
}

@Immutable
data class AppTextColors(
    val primary: Color,
    val secondary: Color,
    val selected: Color,
    val tertiriary: Color,
) {
    companion object {
        internal val default = AppTextColors(
            primary = MainBlack,
            secondary = MainPastel,
            selected = OrangeSelect,
            tertiriary = SecondaryBlack,
        )
    }
}

@Immutable
data class AppButtonColors(
    val primary: Color,
    val secondary: Color,
    val secondaryDarker: Color,
) {
    companion object {
        internal val default = AppButtonColors(
            primary = MainBlack,
            secondary = MainPastel,
            secondaryDarker = DarkerPastel,
        )
    }
}

@Immutable
data class AppSnackbarColors(
    val background: AppSnackbarBackgroundColors,
    val text: AppSnackbarTextColors,
) {
    companion object {
        internal val default = AppSnackbarColors(
            background = AppSnackbarBackgroundColors.default,
            text = AppSnackbarTextColors.default,
        )
    }
}

@Immutable
data class AppSnackbarBackgroundColors(
    val positive: Color,
    val negative: Color,
    val neutral: Color,
) {
    companion object {
        internal val default = AppSnackbarBackgroundColors(
            positive = BackgroundSnackbarPositive,
            negative = BackgroundSnackbarNegative,
            neutral = BackgroundSnackbarNeutral,
        )
    }
}

@Immutable
data class AppSnackbarTextColors(
    val positive: Color,
    val negative: Color,
    val neutral: Color,
) {
    companion object {
        internal val default = AppSnackbarTextColors(
            positive = Color.White,
            negative = Color.White,
            neutral = MainBlack,
        )
    }
}

internal val LocalColors = staticCompositionLocalOf { AppColors.create() }