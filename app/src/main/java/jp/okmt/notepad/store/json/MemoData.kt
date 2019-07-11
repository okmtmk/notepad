package jp.okmt.notepad.store.json

import jp.okmt.notepad.memo.Memo
import java.time.Instant

data class MemoData(
    val id: Int,
    val title: String,
    val noteText: String,
    val created_at: Instant,
    val updated_at: Instant
) {
    constructor(memo: Memo) : this(memo.id!!, memo.title, memo.noteText, memo.created_at, memo.updated_at)
}