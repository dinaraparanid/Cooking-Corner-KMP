package com.paranid5.cooking_corner.feature.main.recipe.presentation.detailed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.recipe_cooking_time
import com.paranid5.cooking_corner.core.resources.recipe_portions
import com.paranid5.cooking_corner.core.resources.recipe_portions_value
import com.paranid5.cooking_corner.core.resources.recipe_prep_time
import com.paranid5.cooking_corner.core.resources.unit_minute
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CookingDetails(
    preparingTime: Int,
    cookingTime: Int,
    portions: Int,
    modifier: Modifier = Modifier,
) = Row(modifier) {
    LabelWithDescription(
        title = stringResource(Res.string.recipe_prep_time),
        description = "$preparingTime ${stringResource(Res.string.unit_minute)}",
        modifier = Modifier.weight(1F),
    )

    LabelWithDescription(
        title = stringResource(Res.string.recipe_cooking_time),
        description = "$cookingTime ${stringResource(Res.string.unit_minute)}",
        modifier = Modifier.weight(1F),
    )

    LabelWithDescription(
        title = stringResource(Res.string.recipe_portions),
        description = stringResource(Res.string.recipe_portions_value, portions),
        modifier = Modifier.weight(1F),
    )
}

@Composable
private fun LabelWithDescription(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Text(
        text = title,
        modifier = Modifier.align(Alignment.CenterHorizontally),
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.regular,
        fontFamily = AppTheme.typography.RalewayFontFamily,
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.minimum))

    Text(
        text = description,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.align(Alignment.CenterHorizontally),
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.body,
        fontFamily = AppTheme.typography.RalewayFontFamily,
    )
}
