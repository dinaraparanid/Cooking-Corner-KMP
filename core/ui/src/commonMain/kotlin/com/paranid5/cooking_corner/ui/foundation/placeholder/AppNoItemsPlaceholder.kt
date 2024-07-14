package com.paranid5.cooking_corner.ui.foundation.placeholder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.no_items_placeholder
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource

private val ICON_SIZE = 18.dp

@Composable
fun AppNoItemsPlaceholder(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    descriptionModifier: Modifier = Modifier,
    descriptionContent: @Composable ColumnScope.() -> Unit,
) = Column(modifier) {
    NoItemsPlaceholderImage(imageModifier.align(Alignment.CenterHorizontally))

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))

    NotItemsPlaceholderDescription(
        modifier = descriptionModifier.align(Alignment.CenterHorizontally),
        content = descriptionContent,
    )

    Spacer(Modifier.height(AppTheme.dimensions.padding.medium))
}

@Composable
private fun NoItemsPlaceholderImage(modifier: Modifier = Modifier) = Image(
    painter = painterResource(Res.drawable.no_items_placeholder),
    contentDescription = null,
    modifier = modifier,
)

@Composable
private fun NotItemsPlaceholderDescription(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.small),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
)

@Composable
fun AppNoItemsPlaceholderDescriptionRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.padding.small),
    content = content,
)

@Composable
fun AppNoItemsPlaceholderDescriptionText(
    text: String,
    modifier: Modifier = Modifier,
) = AppMainText(
    text = text,
    style = AppTheme.typography.regular,
    overflow = TextOverflow.Ellipsis,
    modifier = modifier,
)

@Composable
fun AppNoItemsPlaceholderDescriptionIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
) = Image(
    imageVector = icon,
    contentDescription = null,
    modifier = modifier.size(ICON_SIZE),
)
