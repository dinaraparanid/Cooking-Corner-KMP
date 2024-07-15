package com.paranid5.cooking_corner.feature.main.recipe_editor.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import com.paranid5.cooking_corner.core.common.AppDispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.activation.MimetypesFileTypeMap
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter

@Composable
internal actual fun RecipeCoverPickerButton(
    modifier: Modifier,
    onPicked: (ByteArray) -> Unit
) {
    var fileChooser by remember { mutableStateOf<JFileChooser?>(null) }
    val coroutineScope = rememberCoroutineScope()

    SwingPanel(
        background = Color.Transparent,
        factory = {
            JFileChooser().apply {
                fileFilter = object : FileFilter() {
                    override fun accept(file: File?): Boolean = file?.isImage == true
                    override fun getDescription(): String = ""
                }
            }
        },
        update = { fileChooser = it },
    )

    RecipeCoverPickerButtonUi(
        modifier = modifier,
        onClick = {
            when (fileChooser?.showOpenDialog(null)) {
                JFileChooser.APPROVE_OPTION -> coroutineScope.launch {
                    val bytes = withContext(AppDispatchers.Data) {
                        fileChooser?.selectedFile?.readBytes()
                    }

                    bytes?.let(onPicked)
                }
            }
        },
    )
}

internal inline val File.isImage
    get() = MimetypesFileTypeMap()
        .getContentType(this)
        .split("/")[0] == "image"