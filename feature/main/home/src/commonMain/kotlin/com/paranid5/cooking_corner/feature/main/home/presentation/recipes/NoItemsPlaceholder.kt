package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.home_no_item_placeholder_row_1
import com.paranid5.cooking_corner.core.resources.home_no_item_placeholder_row_2_1
import com.paranid5.cooking_corner.core.resources.home_no_item_placeholder_row_2_2
import com.paranid5.cooking_corner.core.resources.home_no_item_placeholder_row_3
import com.paranid5.cooking_corner.core.resources.home_no_item_placeholder_row_4
import com.paranid5.cooking_corner.core.resources.home_no_item_placeholder_row_5
import com.paranid5.cooking_corner.core.resources.ic_add
import com.paranid5.cooking_corner.core.resources.ic_generate
import com.paranid5.cooking_corner.core.resources.no_items_placeholder
import com.paranid5.cooking_corner.core.resources.search_tab
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

private val ICON_SIZE = 18.dp

@Composable
internal fun NoItemsPlaceholder(modifier: Modifier = Modifier) =
    Column(modifier.verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(AppTheme.dimensions.padding.extraLarge))
        NoItemsPlaceholderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))
        NotItemsPlaceholderDescription(Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.height(AppTheme.dimensions.padding.medium))
    }

@Composable
private fun NoItemsPlaceholderImage(modifier: Modifier = Modifier) = Image(
    painter = painterResource(Res.drawable.no_items_placeholder),
    contentDescription = null,
    modifier = modifier,
)

@Composable
private fun NotItemsPlaceholderDescription(modifier: Modifier = Modifier) =
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.small),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_1))

        DescriptionRow(
            persistentListOf(
                { DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_2_1)) },
                { DescriptionIcon(vectorResource(Res.drawable.search_tab)) },
                { DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_2_2)) },
            )
        )

        DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_3))

        DescriptionRow(
            persistentListOf(
                { DescriptionIcon(vectorResource(Res.drawable.ic_generate)) },
                { DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_4)) },
            )
        )

        DescriptionRow(
            persistentListOf(
                { DescriptionIcon(vectorResource(Res.drawable.ic_add)) },
                { DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_5)) },
            )
        )
    }

@Composable
private fun DescriptionRow(
    items: ImmutableList<@Composable () -> Unit>,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.small)
) {
    items.forEach { it() }
}

@Composable
private fun DescriptionText(
    text: String,
    modifier: Modifier = Modifier,
) = AppMainText(
    text = text,
    style = AppTheme.typography.regular,
    overflow = TextOverflow.Ellipsis,
    modifier = modifier,
)

@Composable
private fun DescriptionIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
) = Image(
    imageVector = icon,
    contentDescription = null,
    modifier = modifier.size(ICON_SIZE),
)
