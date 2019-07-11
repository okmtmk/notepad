package jp.okmt.notepad.store.json

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class JsonStoreIndex(var nextId: Int) {
    companion object {
        private const val indexFilePath = "${LocalMemoStore.locate}/index.json"

        fun get(): JsonStoreIndex {
            File(indexFilePath).also { file ->
                if (file.exists()) {
                    return jacksonObjectMapper().readValue(file)
                } else {
                    JsonStoreIndex(1).apply {
                        jacksonObjectMapper().writeValue(file, this)
                        return this
                    }
                }
            }
        }
    }

    fun save() {
        jacksonObjectMapper().writeValue(File(indexFilePath), this)
    }
}