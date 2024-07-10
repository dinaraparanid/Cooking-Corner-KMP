package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.ic_arrow_down
import com.paranid5.cooking_corner.ui.theme.AppTheme
import com.paranid5.cooking_corner.utils.buildPersistentList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.vectorResource

@Composable
fun <T> AppSpinnerWithArrow(
    selectedItemIndex: Int,
    items: ImmutableList<T>,
    title: T.() -> String,
    initial: String,
    modifier: Modifier = Modifier,
    onItemSelected: (index: Int) -> Unit,
) = Box(modifier.background(AppTheme.colors.background.primary)) {
    AppSpinnerContent(
        selectedItemIndex = selectedItemIndex,
        items = items,
        title = title,
        initial = initial,
        onItemSelected = onItemSelected,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterStart)
            .padding(
                vertical = AppTheme.dimensions.padding.small,
                horizontal = AppTheme.dimensions.padding.small,
            ),
    )

    AppSpinnerArrow(
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = AppTheme.dimensions.padding.big),
    )
}

@Composable
private fun <T> AppSpinnerContent(
    selectedItemIndex: Int,
    items: ImmutableList<T>,
    title: T.() -> String,
    initial: String,
    modifier: Modifier = Modifier,
    onItemSelected: (index: Int) -> Unit,
) {
    val titles by rememberTitles(
        items = items,
        title = title,
        initial = initial,
    )

    AppSpinner(
        modifier = modifier,
        items = titles,
        selectedItemIndices = persistentListOf(selectedItemIndex),
        onItemSelected = { index, _ -> onItemSelected(index) }
    )
}

@Composable
private fun AppSpinnerArrow(modifier: Modifier = Modifier) = Image(
    imageVector = vectorResource(Res.drawable.ic_arrow_down),
    contentDescription = null,
    modifier = modifier,
)

@Composable
private fun <T> rememberTitles(
    items: ImmutableList<T>,
    title: T.() -> String,
    initial: String,
): State<ImmutableList<String>> = remember(items, title, initial) {
    derivedStateOf {
        buildPersistentList {
            add(initial)
            addAll(items.map(title))
        }
    }
}