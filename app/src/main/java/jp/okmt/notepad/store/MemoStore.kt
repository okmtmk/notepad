package jp.okmt.notepad.store

import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.memo.MemoIndex

interface MemoStore {
    val memoList: List<MemoIndex>

    fun read(id: Long): Memo

    fun write(memo: Memo):Long

    fun delete(memo: Memo): Boolean
}