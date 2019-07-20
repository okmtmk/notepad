package jp.okmt.notepad.memo

import java.time.Instant
import java.time.format.DateTimeFormatter

data class MemoIndex(
    val id: Long,
    val title: String,
    private val cratedInstant: Instant,
    private val updatedInstant: Instant
) {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val createdAt = formatter.format(
        cratedInstant.atZone(
            java.time.ZoneId.systemDefault()
        )
    )

    val updatedAt = formatter.format(
        updatedInstant.atZone(
            java.time.ZoneId.systemDefault()
        )
    )
}