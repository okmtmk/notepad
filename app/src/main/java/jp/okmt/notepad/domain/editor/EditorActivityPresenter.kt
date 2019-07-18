package jp.okmt.notepad.domain.editor

import android.content.Context
import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.store.sqlite.MemoDatabaseStore

class EditorActivityPresenter(val view: EditorActivityContract.View, context: Context, id: Long) :
    EditorActivityContract.Presenter {
    override val isCreateNew: Boolean
        get() = memo.id == null

    val memo: Memo = if (id == -1L) {
        Memo.create(MemoDatabaseStore(context))
    } else {
        Memo.load(id, MemoDatabaseStore(context)).apply {
            view.title = title
            view.noteText = noteText
        }
    }

    override fun saveMemo(title: String, noteText: String): Long {
        memo.title = title
        memo.noteText = noteText
        return memo.save()
    }


}