package jp.okmt.notepad.memo

import jp.okmt.notepad.store.MemoStore
import java.time.Instant

class Memo(
    private var store: MemoStore,
    val id: Long?,
    var title: String,
    var noteText: String,
    val createdAt: Instant,
    var updatedAt: Instant
) {
    companion object {
        fun create(store: MemoStore, title: String, noteText: String = ""): Memo {
            return Memo(store, null, title, noteText, Instant.now(), Instant.now())
        }

        fun load(id: Long, store: MemoStore): Memo {
            return store.read(id)
        }

        fun getMemoIndexes(store: MemoStore): List<MemoIndex> {
            return store.memoList
        }
    }

    fun save() {
        updatedAt = Instant.now()
        store.write(this)
    }

    fun remove(): Boolean {
        return store.remove(this)
    }

    fun toMemoIndex(): MemoIndex {
        return MemoIndex(id!!, title, createdAt.toString(), updatedAt.toString())
    }
}