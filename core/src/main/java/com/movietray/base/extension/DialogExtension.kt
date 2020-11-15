package com.movietray.base.extension

import android.content.Context
import com.movietray.base.utils.dialog.AlertDialogView

/**
 * @author Niharika.Arora
 */
inline fun buildDialog(
    context: Context,
    buildAlertAlertDialogView: AlertDialogView.AlertDialogBuilder.() -> Unit
): AlertDialogView {
    val builder = AlertDialogView.AlertDialogBuilder()

    builder.context = context
    builder.buildAlertAlertDialogView()
    return builder.build()
}

fun AlertDialogView.AlertDialogBuilder.title(title: String) {
    titleText = title
}

fun AlertDialogView.AlertDialogBuilder.negativeAction(
    negativeText: String,
    onNegativeClickAction: () -> () -> Unit
) {
    this.onNegativeClickAction = onNegativeClickAction()
    this.negativeText = negativeText
}

fun AlertDialogView.AlertDialogBuilder.positiveAction(
    positiveText: String,
    onPositiveClickAction: () -> () -> Unit
) {
    this.onPositiveClickAction = onPositiveClickAction()
    this.positiveText = positiveText
}