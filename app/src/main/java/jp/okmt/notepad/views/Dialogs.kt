package jp.okmt.notepad.views

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object Dialogs {
    fun showDialog(
        context: Context,
        title: String,
        message: String,
        onOkClickListener: DialogInterface.OnClickListener?,
        onCancelClickListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(android.R.string.yes, onOkClickListener)
            setNegativeButton(android.R.string.cancel, onCancelClickListener)
            show()
        }
    }
}