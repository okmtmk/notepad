package jp.okmt.notepad.memo

import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import jp.okmt.notepad.store.MemoStore
import jp.okmt.notepad.store.sqlite.MemoDatabaseStore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MemoAndroidTest {
    private val context: Context
        get() = InstrumentationRegistry.getTargetContext()

    private val store: MemoStore
        get() = MemoDatabaseStore(context)

    @Test
    fun testSaveMemo() {
        val memo = Memo.create("title", "メモ本文")
        memo.save(store)
    }

    @Test
    fun testGetMemoIndexes() {
        val memos = Memo.getMemoIndexes(store)
        assert(memos.isNotEmpty())
    }

    @Test
    fun testLoadMemo() {
        val memo = Memo.load(Memo.getMemoIndexes(store)[0].id, store)
    }

    @Test
    fun testRemoveMemo() {
        val memo = Memo.load(Memo.getMemoIndexes(store)[0].id, store)
        memo.remove(store)
    }
}