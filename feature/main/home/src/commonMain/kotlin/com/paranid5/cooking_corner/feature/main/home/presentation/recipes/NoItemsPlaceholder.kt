package com.paranid5.cooking_corner.feature.main.home.presentation.recipes

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.paranid5.cooking_corner.core.resources.search_tab
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppContentStub
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppContentStubDescriptionIcon as DescriptionIcon
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppContentStubDescriptionRow as DescriptionRow
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppContentStubDescriptionText as DescriptionText

private val IMAGE_SIZE = 265.dp

@Composable
internal fun NoItemsPlaceholder(modifier: Modifier = Modifier) =
    AppContentStub(
        modifier = modifier,
        imageModifier = Modifier.size(IMAGE_SIZE),
    ) {
        DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_1))

        DescriptionRow {
            DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_2_1))
            DescriptionIcon(vectorResource(Res.drawable.search_tab))
            DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_2_2))
        }

        DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_3))

        DescriptionRow {
            DescriptionIcon(vectorResource(Res.drawable.ic_generate))
            DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_4))
        }

        DescriptionRow {
            DescriptionIcon(vectorResource(Res.drawable.ic_add))
            DescriptionText(stringResource(Res.string.home_no_item_placeholder_row_5))
        }
    }
