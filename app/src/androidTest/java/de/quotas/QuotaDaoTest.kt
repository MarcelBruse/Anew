package de.quotas

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.quotas.models.Quota
import de.quotas.persistency.QuotaDao
import de.quotas.persistency.QuotasDatabase
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class QuotaDaoTest {

    private lateinit var database: QuotasDatabase

    private lateinit var quotaDao: QuotaDao

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, QuotasDatabase::class.java).build()
        quotaDao = database.getQuotaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun queryForNonExistentQuota() {
        assertThat(getValue(quotaDao.load(42L))).isNull()
    }

    @Test
    fun insertNewQuota() {
        val id = 42L
        val name = "Quota"
        quotaDao.save(Quota(id, name))
        val quota = getValue(quotaDao.load(id))
        assertThat(quota).isNotNull
        assertThat(quota?.id).isEqualTo(id)
        assertThat(quota?.name).isEqualTo(name)
    }

    private fun getValue(liveData: LiveData<Quota>): Quota? {
        var quota: Quota? = null
        val latch = CountDownLatch(1)
        Handler(Looper.getMainLooper()).post {
            liveData.observeForever { result ->
                quota = result
                latch.countDown()
            }
        }
        latch.await(2, TimeUnit.SECONDS)
        return quota
    }

}
