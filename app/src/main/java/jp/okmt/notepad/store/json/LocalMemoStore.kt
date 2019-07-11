package jp.okmt.notepad.store.json

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.store.MemoStore
import java.io.File

class LocalMemoStore : MemoStore {
    companion object {
        const val locate = "memo/"
    }

    private fun createFileInstance(id: Int): File {
        return File("$locate$id.json")
    }

    override fun read(id: Int): Memo {
        val memoData = jacksonObjectMapper().readValue<MemoData>(createFileInstance(id))
        return Memo(memoData.id, memoData.title, memoData.noteText, memoData.created_at, memoData.updated_at)
    }

    override fun write(memo: Memo) {
        val memoData = MemoData(memo)
        jacksonObjectMapper().writeValue(createFileInstance(memoData.id), memoData)
    }

    override fun remove(memo: Memo): Boolean {
        memo.id ?: return false

        createFileInstance(memo.id).apply {
            return if (this.exists()) {
                this.delete()
            } else {
                false
            }
        }
    }
}