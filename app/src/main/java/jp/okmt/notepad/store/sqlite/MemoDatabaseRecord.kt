package jp.okmt.notepad.store.sqlite

data class MemoDatabaseRecord(
    var id: Long?,
    val title: String,
    var filePath: String?,
    val createdAt: String,
    val updatedAt: String
)