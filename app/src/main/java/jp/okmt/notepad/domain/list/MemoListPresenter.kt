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

    override fun deleteMemo(context: Context, index: MemoIndex) {
        deleteListItem(index)
        MemoDatabaseStore(context).let { store ->
            Memo.load(index.id, store).apply {
                delete()
            }
        }
    }

    override fun deleteListItem(elem: MemoIndex) {
        indexes!!.remove(elem)
    }

    override fun addMemoToList(context: Context, memo_id: Long): Int {
        Memo.load(memo_id, MemoDatabaseStore(context)).let { memo ->
            indexes!!.add(memo.toMemoIndex())
        }
        return indexes!!.size
    }

    override fun updateMemoItem(context: Context, memoId: Long): Int {
        indexes?.forEachIndexed { index, memoIndex ->
            if (memoIndex.id == memoId) {
                indexes?.set(index, Memo.load(memoId, MemoDatabaseStore(context)).toMemoIndex())
                return index
            }
        }
        return -1
    }
}