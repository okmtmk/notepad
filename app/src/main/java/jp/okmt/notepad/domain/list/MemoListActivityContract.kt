package jp.okmt.notepad.domain.list

import android.content.Context
import jp.okmt.notepad.memo.MemoIndex

interface MemoListActivityContract {
    interface View {
        fun notifySnackBar(message: String)
    }

    interface Presenter {
        fun getMemoIndexes(context: Context): MutableList<MemoIndex>
        fun addMemoToList(context: Context, memo_id: Long): Int
        fun deleteMemo(context: Context, index: MemoIndex)
        fun deleteListItem(elem: MemoIndex)
    }
}