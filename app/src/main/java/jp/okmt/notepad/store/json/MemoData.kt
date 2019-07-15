package jp.okmt.notepad.store.json

import jp.okmt.notepad.memo.Memo

data class MemoData(
    val id: Long,
    val title: String,
    val noteText: String,
    val createdAt: String,
    val updatedAt: String
) {
    constructor(memo: Memo) : this(
        memo.id!!,
        memo.title,
        memo.noteText,
        memo.createdAt.toString(),
        memo.updatedAt.toString()
    )

    constructor(id: Long, memo: Memo) : this(
        id,
        memo.title,
        memo.noteText,
        memo.createdAt.toString(),
        memo.updatedAt.toString()
    )
}