package com.paranid5.cooking_corner.ui.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.paranid5.cooking_corner.ui.theme.AppTheme

@Composable
fun AppProgressIndicator(modifier: Modifier = Modifier) = Box(modifier) {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
        color = AppTheme.colors.orange,
    )
}