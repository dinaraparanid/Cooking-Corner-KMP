package com.paranid5.cooking_corner.ui.foundation.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher

@Composable
actual fun ImagePickerLauncher(onPicked: (ByteArray) -> Unit): () -> Unit {
    val scope = rememberCoroutineScope()

    val imagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { onPicked(it) }
        },
    )

    return imagePicker::launch
}