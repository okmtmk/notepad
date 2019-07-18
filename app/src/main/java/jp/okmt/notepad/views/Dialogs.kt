package jp.okmt.notepad.views

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import jp.okmt.notepad.R

object Dialogs {
    fun showMemoDeleteDialog(context: Context, listener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context).apply {
            setMessage(R.string.memo_delete_confirm)
            setPositiveButton(android.R.string.yes, listener)
            setNegativeButton(android.R.string.cancel, null)
            show()
        }
    }

    fun showConfirmReturnBackDialog(context: Context, listener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context).apply {
            setTitle(R.string.confirm_return_back)
            setMessage(R.string.confirm_return_back_desc)
            setPositiveButton(android.R.string.yes, listener)
            setNegativeButton(android.R.string.cancel, null)
            show()
        }
    }
}