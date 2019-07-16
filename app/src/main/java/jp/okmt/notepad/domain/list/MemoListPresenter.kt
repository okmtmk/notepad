package jp.okmt.notepad.domain.list

import android.content.Context
import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.memo.MemoIndex
import jp.okmt.notepad.store.sqlite.MemoDatabaseStore

class MemoListPresenter(val view: MemoListActivityContract.View) :
    MemoListActivityContract.Presenter {

    override fun getMemoIndexes(context: Context): List<MemoIndex> {
        return Memo.getMemoIndexes(MemoDatabaseStore(context))
    }

    override fun deleteMemo(context: Context, id: Long) {
        MemoDatabaseStore(context).let { store ->
            Memo.load(id, store).apply {
                remove(store)
            }
        }
    }
}