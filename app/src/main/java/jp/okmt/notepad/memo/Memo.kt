package jp.okmt.notepad.memo

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

        fun load(id: Int) {
            //todo 読み込み処理
        }
    }


}