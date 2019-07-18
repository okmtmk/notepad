package jp.okmt.notepad.store.json

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.memo.MemoIndex
import jp.okmt.notepad.store.MemoStore
import java.io.File
import java.time.Instant

class LocalMemoStore : MemoStore {
    companion object {
        const val locate = "../memo"
        const val memoLocate = "$locate/notes"
    }

    init {
        File(locate).let { file ->
            if (!file.exists()) {
                file.mkdir()
            }
        }

        File(memoLocate).let { file ->
            if (!file.exists()) {
                file.mkdir()
            }
        }
    }

    private val index = JsonStoreIndex.get()

    override val memoList: List<MemoIndex>
        get() {
            val list = mutableListOf<MemoIndex>()
            File(memoLocate).listFiles().forEach {
                jacksonObjectMapper().readValue<MemoData>(it).apply {
                    list.add(MemoIndex(id, title, createdAt, updatedAt))
                }
            }
            return list
        }

    private fun createFileInstance(id: Long): File {
        return File("$memoLocate/$id.json")
    }

    override fun read(id: Long): Memo {
        val memoData = jacksonObjectMapper().readValue<MemoData>(createFileInstance(id))
        return Memo(
            this,
            memoData.id,
            memoData.title,
            memoData.noteText,
            Instant.parse(memoData.createdAt),
            Instant.parse(memoData.updatedAt)
        )
    }

    override fun write(memo: Memo) {
        val memoData =
            if (memo.id == null) {
                val id = index.nextId

                index.apply {
                    nextId++
                    save()
                }

                MemoData(id, memo)
            } else
                MemoData(memo)

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