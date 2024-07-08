package com.paranid5.cooking_corner.ui.foundation.alert_dialog

class AppAlertDialogCallbacks(
    val onDismissRequest: () -> Unit,
    val onConfirmButtonClick: () -> Unit,
    val onCancelButtonClick: (() -> Unit)? = null,
)