package com.paranid5.cooking_corner.ui.foundation.picker

import androidx.compose.runtime.Composable

@Composable
expect fun ImagePickerLauncher(onPicked: (ByteArray) -> Unit): () -> Unit