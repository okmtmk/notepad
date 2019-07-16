package jp.okmt.notepad.store.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.util.*

object MemoDatabaseRepository {
    /**
     * レコードの挿入
     */
    fun insert(context: Context, record: MemoDatabaseRecord): MemoDatabaseRecord {
        MemoDatabaseHelper(context).writableDatabase.let { db ->
            record.id = db.insert(
                MemoDatabaseHelper.TABLE_NAME,
                null,
                putValues(record)
            )
            db.close()
        }
        return record
    }

    /**
     * レコードの更新
     */
    fun update(context: Context, record: MemoDatabaseRecord): MemoDatabaseRecord {
        if (record.id == null) {
            throw NullPointerException()
        }

        MemoDatabaseHelper(context).writableDatabase.let { db ->
            db.update(
                MemoDatabaseHelper.TABLE_NAME,
                putValues(record),
                "${MemoDatabaseHelper.ID}=?",
                arrayOf(record.id.toString())
            )
            db.close()
        }
        return record
    }

    /**
     * レコードの取得
     */
    fun find(context: Context, id: Long): MemoDatabaseRecord {
        MemoDatabaseHelper(context).readableDatabase.query(
            MemoDatabaseHelper.TABLE_NAME,
            arrayOf(
                MemoDatabaseHelper.ID,
                MemoDatabaseHelper.TITLE,
                MemoDatabaseHelper.FILEPATH,
                MemoDatabaseHelper.CREATED_AT,
                MemoDatabaseHelper.UPDATED_AT
            ),
            "${MemoDatabaseHelper.ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).apply {
            moveToFirst()
            val record = makeRecord(this)
            close()
            return record
        }
    }

    /**
     * レコードのリスト取得
     */
    fun get(context: Context, count: Int? = null): List<MemoDatabaseRecord> {
        MemoDatabaseHelper(context).readableDatabase.query(
            MemoDatabaseHelper.TABLE_NAME,
            arrayOf(
                MemoDatabaseHelper.ID,
                MemoDatabaseHelper.TITLE,
                MemoDatabaseHelper.FILEPATH,
                MemoDatabaseHelper.CREATED_AT,
                MemoDatabaseHelper.UPDATED_AT
            ), null, null, null, null, null, count?.toString()
        ).apply {
            val mutableList = mutableListOf<MemoDatabaseRecord>()

            moveToFirst()
            while (!isLast) {
                mutableList.add(makeRecord(this))
                moveToNext()
            }

            close()
            return mutableList.toList()
        }

    }

    /**
     * レコードの削除
     */
    fun delete(context: Context, id: Long) {
        MemoDatabaseHelper(context).writableDatabase.let { db ->
            db.delete(
                MemoDatabaseHelper.TABLE_NAME,
                "${MemoDatabaseHelper.ID}=?",
                arrayOf(id.toString())
            )
        }
    }

    /**
     * ランダムな文字列を発行する
     */
    fun getFileName(context: Context): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

        var text: String
        do {
            text = ""
            (0..19).forEach { i ->
                text += chars[Random().nextInt(chars.length)]
            }
        } while (isDuplicateFilename(context, text))

        return text
    }

    /**
     * ファイル名の重複を確認します
     * @return 重複しているか否か
     */
    private fun isDuplicateFilename(context: Context, filename: String): Boolean {
        val cursor = MemoDatabaseHelper(context).readableDatabase.query(
            MemoDatabaseHelper.TABLE_NAME,
            arrayOf(
                MemoDatabaseHelper.ID, MemoDatabaseHelper.FILEPATH
            ),
            "${MemoDatabaseHelper.FILEPATH} = ?",
            arrayOf(filename),
            null,
            null,
            null,
            null
        )

        val count = cursor.count
        cursor.close()

        return count > 0
    }

    /**
     * recordオブジェクトからContentValuesをputする
     */
    private fun putValues(record: MemoDatabaseRecord): ContentValues {
        ContentValues().apply {
            put(MemoDatabaseHelper.TITLE, record.title)
            put(MemoDatabaseHelper.FILEPATH, record.filePath)
            put(MemoDatabaseHelper.CREATED_AT, record.created_at)
            put(MemoDatabaseHelper.UPDATED_AT, record.updated_at)
            return this
        }
    }

    /**
     * Cursorをrecordオブジェクトに変換する
     */
    private fun makeRecord(cursor: Cursor): MemoDatabaseRecord {
        cursor.apply {
            return MemoDatabaseRecord(
                getLong(getColumnIndex(MemoDatabaseHelper.ID)),
                getString(getColumnIndex(MemoDatabaseHelper.TITLE)),
                getString(getColumnIndex(MemoDatabaseHelper.FILEPATH)),
                getString(getColumnIndex(MemoDatabaseHelper.CREATED_AT)),
                getString(getColumnIndex(MemoDatabaseHelper.UPDATED_AT))
            )
        }
    }
}