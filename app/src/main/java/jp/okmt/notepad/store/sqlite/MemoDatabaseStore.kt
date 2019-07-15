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
                record.id,
                record.title,
                "test",
                Instant.parse(record.created_at),
                Instant.parse(record.updated_at)
            )
        }
    }

    override fun write(memo: Memo) {
        if (memo.id == null) {
            //todo ファイルへセーブする処理
            val filepath = "test"

            MemoDatabaseRepository.insert(
                context,
                MemoDatabaseRecord(
                    null,
                    memo.title,
                    filepath,
                    memo.createdAt.toString(),
                    memo.updatedAt.toString()
                )
            )
        } else {
            MemoDatabaseRepository.find(context, memo.id).let { record ->
                //todo ファイルへセーブする処理

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
        }
    }

    override fun remove(memo: Memo): Boolean {
        memo.id ?: return false

        MemoDatabaseRepository.find(context, memo.id).let { record ->
            //todo ファイルを削除する処理

            MemoDatabaseRepository.delete(context, memo.id)
        }
        return true
    }
}