package jp.okmt.notepad.store.sqlite

data class MemoDatabaseRecord(
    var id: Long?,
    val title: String,
    var filePath: String?,
    val created_at: String,
    val updated_at: String
)