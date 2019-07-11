package jp.okmt.notepad.memo

import jp.okmt.notepad.data.MemoStore
import java.time.Instant

class Memo(
    val id: Int?,
    var title: String,
    var noteText: String,
    val created_at: Instant,
    var updated_at: Instant
) {
    companion object {
        fun create(title: String, noteText: String): Memo {
            return Memo(null, title, noteText, Instant.now(), Instant.now())
        }

        fun load(id: Int, store: MemoStore) {
            //todo 読み込み処理
        }
    }

    fun save(store: MemoStore) {
        store.write(this)
    }

    fun remove(store: MemoStore) {
        store.remove(this)
    }
}