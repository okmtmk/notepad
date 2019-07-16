package jp.okmt.notepad.memo

import jp.okmt.notepad.store.MemoStore
import java.time.Instant

class Memo(
    val id: Long?,
    var title: String,
    var noteText: String,
    val createdAt: Instant,
    var updatedAt: Instant
) {
    companion object {
        fun create(title: String, noteText: String = ""): Memo {
            return Memo(null, title, noteText, Instant.now(), Instant.now())
        }

        fun load(id: Long, store: MemoStore): Memo {
            return store.read(id)
        }

        fun getMemoIndexes(store: MemoStore): List<MemoIndex> {
            return store.memoList
        }
    }

    fun save(store: MemoStore) {
        updatedAt = Instant.now()
        store.write(this)
    }

    fun remove(store: MemoStore): Boolean {
        return store.remove(this)
    }
}