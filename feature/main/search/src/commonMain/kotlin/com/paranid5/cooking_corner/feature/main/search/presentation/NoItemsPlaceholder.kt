package com.paranid5.cooking_corner.feature.main.search.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.paranid5.cooking_corner.core.resources.Res
import com.paranid5.cooking_corner.core.resources.home_tab
import com.paranid5.cooking_corner.core.resources.search_last_recipes_no_item_placeholder_row_1
import com.paranid5.cooking_corner.core.resources.search_last_recipes_no_item_placeholder_row_2_1
import com.paranid5.cooking_corner.core.resources.search_last_recipes_no_item_placeholder_row_2_2
import com.paranid5.cooking_corner.core.resources.search_last_recipes_no_item_placeholder_row_3
import com.paranid5.cooking_corner.core.resources.search_last_recipes_no_item_placeholder_row_4
import com.paranid5.cooking_corner.core.resources.search_matching_results_none
import com.paranid5.cooking_corner.core.resources.search_tab
import com.paranid5.cooking_corner.ui.foundation.AppMainText
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppContentStub
import com.paranid5.cooking_corner.ui.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppContentStubDescriptionIcon as DescriptionIcon
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppContentStubDescriptionRow as DescriptionRow
import com.paranid5.cooking_corner.ui.foundation.placeholder.AppContentStubDescriptionText as DescriptionText

private val LATEST_IMAGE_SIZE = 144.dp
private val FOUND_IMAGE_SIZE = 265.dp

@Composable
internal fun LatestRecipesNoItemsPlaceholder(modifier: Modifier = Modifier) =
    AppContentStub(
        modifier = modifier,
        imageModifier = Modifier.size(LATEST_IMAGE_SIZE),
    ) {
        DescriptionText(stringResource(Res.string.search_last_recipes_no_item_placeholder_row_1))

        DescriptionRow {
            DescriptionText(stringResource(Res.string.search_last_recipes_no_item_placeholder_row_2_1))
            DescriptionIcon(vectorResource(Res.drawable.search_tab))
            DescriptionText(stringResource(Res.string.search_last_recipes_no_item_placeholder_row_2_2))
        }

        DescriptionText(stringResource(Res.string.search_last_recipes_no_item_placeholder_row_3))

        DescriptionRow {
            DescriptionIcon(vectorResource(Res.drawable.home_tab))
            DescriptionText(stringResource(Res.string.search_last_recipes_no_item_placeholder_row_4))
        }
    }

@Composable
internal fun FoundRecipesNoItemsPlaceholder(modifier: Modifier = Modifier) =
    AppContentStub(
        modifier = modifier,
        imageModifier = Modifier.size(FOUND_IMAGE_SIZE),
    ) {
        AppMainText(
            text = stringResource(Res.string.search_matching_results_none),
            fontWeight = FontWeight.Bold,
            style = AppTheme.typography.body,
        )
    }
