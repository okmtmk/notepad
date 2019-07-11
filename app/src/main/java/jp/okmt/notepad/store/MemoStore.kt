package jp.okmt.notepad.store

import jp.okmt.notepad.memo.Memo

interface MemoStore {
    fun read(id: Int): Memo

    fun write(memo: Memo)

    fun remove(memo: Memo): Boolean
}