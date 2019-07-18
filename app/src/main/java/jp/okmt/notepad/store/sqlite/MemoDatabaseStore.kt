package jp.okmt.notepad.store.sqlite

import android.content.Context
import jp.okmt.notepad.memo.Memo
import jp.okmt.notepad.memo.MemoIndex
import jp.okmt.notepad.store.MemoStore
import java.time.Instant

class MemoDatabaseStore(private val context: Context) : MemoStore {
    override val memoList: List<MemoIndex>
        get() {
            val mutableList = mutableListOf<MemoIndex>()
            MemoDatabaseRepository.get(context).forEach { memo ->
                mutableList.add(
                    MemoIndex(
                        memo.id!!,
                        memo.title,
                        memo.created_at,
                        memo.updated_at
                    )
                )
            }
            return mutableList.toList()
        }

    override fun read(id: Long): Memo {
        MemoDatabaseRepository.find(context, id).let { record ->
            return Memo(
                this,
                record.id,
                record.title,
                "test",
                Instant.parse(record.created_at),
                Instant.parse(record.updated_at)
            )
        }
    }

    override fun write(memo: Memo): Long {
        if (memo.id == null) {
            val filepath = MemoDatabaseRepository.getFileName(context)
            AndroidLocalFile.write(context, filepath, memo.noteText)

            MemoDatabaseRepository.insert(
                context,
                MemoDatabaseRecord(
                    null,
                    memo.title,
                    filepath,
                    memo.createdAt.toString(),
                    memo.updatedAt.toString()
                )
            ).apply {
                return id!!
            }
        } else {
            MemoDatabaseRepository.find(context, memo.id!!).let { record ->
                AndroidLocalFile.write(context, record.filePath!!, memo.noteText)

                MemoDatabaseRepository.update(
                    context,
                    MemoDatabaseRecord(
                        record.id,
                        memo.title,
                        record.filePath,
                        memo.createdAt.toString(),
                        memo.updatedAt.toString()
                    )
                )
            }
            return memo.id!!
        }
    }

    override fun delete(memo: Memo): Boolean {
        memo.id ?: return false

        MemoDatabaseRepository.find(context, memo.id!!).let { record ->
            AndroidLocalFile.remove(context, record.filePath!!)
            MemoDatabaseRepository.delete(context, memo.id!!)
        }
        return true
    }
}