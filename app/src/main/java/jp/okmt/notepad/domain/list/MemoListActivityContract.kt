package jp.okmt.notepad.domain.list

import android.content.Context
import jp.okmt.notepad.memo.MemoIndex

interface MemoListActivityContract {
    interface View {
        fun notifySnackBar(message: String)
        fun deleteRecyclerViewElem(position: Int)
    }

    interface Presenter {
        fun getMemoIndexes(context: Context): List<MemoIndex>
        fun deleteMemo(context: Context, id: Long)
    }
}