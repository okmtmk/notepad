package jp.okmt.notepad.views.memo_list

import android.view.View
import jp.okmt.notepad.memo.MemoIndex

interface OnMemoListClickListener {
    fun onClick(view: View, position: Int, index: MemoIndex)
}