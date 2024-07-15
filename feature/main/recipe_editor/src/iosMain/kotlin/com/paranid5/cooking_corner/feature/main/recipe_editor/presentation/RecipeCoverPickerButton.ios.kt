package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher

@Composable
internal actual fun RecipeCoverPickerButton(
    modifier: Modifier,
    onPicked: (ByteArray) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val imagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { onPicked(it) }
        },
    )

    RecipeCoverPickerButtonUi(
        modifier = modifier,
        onClick = { imagePicker.launch() },
    )
}