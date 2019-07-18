package jp.okmt.notepad.memo

import jp.okmt.notepad.store.json.LocalMemoStore
import org.junit.Test

class MemoTest {
    companion object {
        private const val title = "テストで作ったメモ"
        private const val noteText = "メモ本文"

        val store = LocalMemoStore()
    }

    @Test
    fun testCreateMemo() {
        Memo.create(store, title, noteText).apply {
            assert(this is Memo)
        }
    }

    @Test
    fun testSaveMemo() {
        Memo.create(store, title, noteText).apply {
            save()
        }
    }

    @Test
    fun testGetMemoList() {
        Memo.getMemoIndexes(store)
    }

    @Test
    fun testReadMemo() {
        Memo.load(Memo.getMemoIndexes(store)[0].id, LocalMemoStore()).apply {
            assert(title == "テストで作ったメモ")
        }
    }

    @Test
    fun testRemoveMemo() {
        store.apply {
            val list = Memo.getMemoIndexes(store)
            assert(Memo.load(list[0].id, this).remove())
        }
    }
}