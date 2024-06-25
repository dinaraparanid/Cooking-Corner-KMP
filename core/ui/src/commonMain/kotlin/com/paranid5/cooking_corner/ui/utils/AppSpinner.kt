package com.paranid5.cooking_corner.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.paranid5.cooking_corner.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AppSpinner(
    items: ImmutableList<String>,
    selectedItemIndices: ImmutableList<Int>,
    onItemSelected: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    dropdownModifier: Modifier = Modifier,
    background: Color = AppTheme.colors.background,
    previewItemIndex: Int = selectedItemIndices.firstOrNull() ?: 0,
    selectedItemFactory: @Composable (Int, String, Modifier) -> Unit = { _, text, mod ->
        DefaultSelectedItem(text, mod)
    },
    previewItemFactory: @Composable (Int, String, Modifier) -> Unit = selectedItemFactory,
    dropdownItemFactory: @Composable (Int, String, Modifier) -> Unit = { _, text, mod ->
        DefaultItem(text, mod)
    },
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier.clickable { isExpanded = true }) {
        items.getOrNull(previewItemIndex)?.let {
            previewItemFactory(previewItemIndex, it, modifier,)
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = dropdownModifier.background(background)
        ) {
            items.forEachIndexed { index, element ->
                DropdownMenuItem(
                    text = {
                        when (index) {
                            in selectedItemIndices ->
                                selectedItemFactory(index, element, Modifier)

                            else -> dropdownItemFactory(index, element, Modifier)
                        }
                    },
                    onClick = {
                        onItemSelected(index, element)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DefaultSelectedItem(text: String, modifier: Modifier = Modifier) {
    val updText by rememberUpdatedState(text)

    Text(
        text = updText,
        modifier = modifier,
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.regular,
        fontWeight = FontWeight.Bold,
        fontFamily = AppTheme.typography.RalewayFontFamily,
    )
}

@Composable
private fun DefaultItem(text: String, modifier: Modifier = Modifier) {
    val updText by rememberUpdatedState(text)

    Text(
        text = updText,
        modifier = modifier,
        color = AppTheme.colors.text.primary,
        style = AppTheme.typography.regular,
        fontFamily = AppTheme.typography.RalewayFontFamily,
    )
}