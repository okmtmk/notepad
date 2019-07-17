package jp.okmt.notepad.domain.list

import android.content.Context
import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.memo.MemoIndex
import jp.okmt.notepad.store.sqlite.MemoDatabaseStore

class MemoListPresenter(val view: MemoListActivityContract.View) :
    MemoListActivityContract.Presenter {
    private var indexes: MutableList<MemoIndex>? = null

    override fun getMemoIndexes(context: Context): MutableList<MemoIndex> {
        if (indexes == null) {
            indexes = Memo.getMemoIndexes(MemoDatabaseStore(context)).toMutableList()
        }
        return indexes!!
    }

    override fun deleteMemo(context: Context, id: Long) {
        MemoDatabaseStore(context).let { store ->
            Memo.load(id, store).apply {
                remove(store)
            }
        }
    }

    override fun deleteListItem(elem: MemoIndex) {
        indexes!!.remove(elem)
    }
}