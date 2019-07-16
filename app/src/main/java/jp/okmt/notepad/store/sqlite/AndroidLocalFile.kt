package jp.okmt.notepad.store.sqlite

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object AndroidLocalFile {
    /**
     * アプリの内部領域にファイルを作成し保存する
     */
    fun write(context: Context, filepath: String, text: String) {
        context.openFileOutput(filepath, Context.MODE_APPEND).write(text.toByteArray())
    }

    /**
     * アプリの内部領域からファイルを読み込む
     */
    fun read(context: Context, filepath: String): String {
        val reader = BufferedReader(InputStreamReader(context.openFileInput(filepath)))

        var text = ""
        reader.lines().forEach {
            text += it
        }
        return text
    }

    fun remove(context: Context, filepath: String) {
        context.deleteFile(filepath)
    }
}