package jp.okmt.notepad.store

import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.memo.MemoIndex

interface MemoStore {
    val memoList: List<MemoIndex>

    fun read(id: Int): Memo

    fun write(memo: Memo)

    fun remove(memo: Memo): Boolean
}