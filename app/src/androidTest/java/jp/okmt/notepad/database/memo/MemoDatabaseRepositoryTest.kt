package jp.okmt.notepad.database.memo

import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import jp.okmt.notepad.store.sqlite.MemoDatabaseRecord
import jp.okmt.notepad.store.sqlite.MemoDatabaseRepository
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant

@RunWith(AndroidJUnit4::class)
class MemoDatabaseRepositoryTest {

    private val context: Context
        get() = InstrumentationRegistry.getTargetContext()

    @Test
    fun testGetFileName() {
        MemoDatabaseRepository.getFileName(context).let { name ->
            assert(name.length == 20)
        }
    }

    @Test
    fun testInsert() {
        MemoDatabaseRepository.insert(
            context,
            MemoDatabaseRecord(
                null,
                "テスト",
                MemoDatabaseRepository.getFileName(context),
                Instant.now().toString(),
                Instant.now().toString()
            )
        ).apply {
            assert(id != null)
        }
    }

    @Test
    fun testGet() {
        MemoDatabaseRepository.get(context).let { records ->
            assert(records.isNotEmpty())
        }
    }

    @Test
    fun testFind() {
        val record = MemoDatabaseRepository.get(context)[0]
        MemoDatabaseRepository.find(context, record.id!!).apply {
            assert(id == record.id)
        }
    }

    @Test
    fun testDelete() {
        var record = MemoDatabaseRepository.get(context)[0]
        MemoDatabaseRepository.delete(context, record.id!!)
    }
}