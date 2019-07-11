package jp.okmt.notepad.data

import jp.okmt.notepad.memo.Memo

interface MemoStore {
    fun read(memo: Memo)

    fun write(memo: Memo)

    fun remove(memo: Memo)
}