package jp.okmt.notepad.memo

import org.junit.Test

class MemoTest {
    @Test
    fun testCreateMemo(){
        Memo.create("テストで作ったメモ", "メモ本文")
    }
}