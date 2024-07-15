package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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

typealias SpinnerItem = @Composable (Int, String, Modifier) -> Unit

@Composable
fun AppSpinner(
    items: ImmutableList<String>,
    selectedItemIndices: ImmutableList<Int>,
    onItemSelected: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    dropdownModifier: Modifier = Modifier,
    background: Color = AppTheme.colors.background.primary,
    previewItemIndex: Int = selectedItemIndices.firstOrNull() ?: 0,
    selectedItemFactory: SpinnerItem = { _, text, mod ->
        DefaultAppSpinnerSelectedItem(text, mod)
    },
    previewItemFactory: SpinnerItem = selectedItemFactory,
    dropdownItemFactory: SpinnerItem = { _, text, mod ->
        DefaultAppSpinnerItem(text, mod)
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
fun DefaultAppSpinnerSelectedItem(text: String, modifier: Modifier = Modifier) {
    val updText by rememberUpdatedState(text)

    AppMainText(
        text = updText,
        modifier = modifier,
        style = AppTheme.typography.regular,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun DefaultAppSpinnerItem(text: String, modifier: Modifier = Modifier) {
    val updText by rememberUpdatedState(text)

    AppMainText(
        text = updText,
        modifier = modifier,
        style = AppTheme.typography.regular,
    )
}