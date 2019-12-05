package de.quotas.persistency

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.quotas.models.Quota
import de.quotas.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class QuotaDaoTest {

    private lateinit var database: QuotasDatabase

    private lateinit var quotaDao: QuotaDao

    private val TEST_QUOTA_ID = 42L

    private val TEST_QUOTA_NAME = "Quota"

    private val TEST_START_TIME = ZonedDateTime.parse("2019-11-13T01:00:00+01:00[Europe/Berlin]")

    private val TEST_LAST_FULFILLMENT_TIME = ZonedDateTime.parse("2019-11-16T12:10:10+01:00[Europe/Berlin]")

    private val SAMPLE_TIME = ZonedDateTime.parse("2019-11-13T07:00:00+01:00[Europe/Berlin]")

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, QuotasDatabase::class.java).build()
        quotaDao = database.getQuotaDao()
        quotaDao.save(Quota(TEST_QUOTA_ID,
            TEST_QUOTA_NAME,
            Weekly(Clock.systemDefaultZone()),
            TEST_START_TIME,
            TEST_LAST_FULFILLMENT_TIME
        ))
    }

    @Test
    fun queryForNonExistentQuota() {
        val quota = getValue(quotaDao.load(12357L))
        assertThat(quota).isNull()
    }

    @Test
    fun saveQuota() {
        val quota = getValue(quotaDao.load(TEST_QUOTA_ID))
        assertThat(quota).isNotNull()
        assertThat(quota?.id).isEqualTo(TEST_QUOTA_ID)
        assertThat(quota?.name).isEqualTo(TEST_QUOTA_NAME)
        assertThat(quota?.period).isEqualTo(Weekly(Clock.systemDefaultZone()))
        assertThat(quota?.startTime).isEqualTo(TEST_START_TIME)
        assertThat(quota?.lastFulfillmentTime).isEqualTo(TEST_LAST_FULFILLMENT_TIME)
    }

    @Test
    fun autoGeneratePrimaryKeysUponQuotaCreation() {
        val name = "First quota"
        quotaDao.save(Quota(0L, name, Weekly(Clock.systemDefaultZone()), SAMPLE_TIME, SAMPLE_TIME))
        val quotas = getValue(quotaDao.loadAll())
        assertThat(quotas).hasSize(2)
        val filterResult = quotas.filter { quota -> quota.id != 0L }
            .filter { quota -> quota.id != TEST_QUOTA_ID }
        assertThat(filterResult).hasSize(1)
        val quota = filterResult.get(0)
        assertThat(quota.name).isEqualTo(name)
    }

    @Test
    fun saveQuotaAlterItAndSaveItAgain() {
        val oldQuota = getValue(quotaDao.load(TEST_QUOTA_ID))
        assertThat(oldQuota?.id).isEqualTo(TEST_QUOTA_ID)
        assertThat(oldQuota?.name).isEqualTo(TEST_QUOTA_NAME)
        assertThat(oldQuota?.period).isEqualTo(Weekly(Clock.systemDefaultZone()))
        assertThat(oldQuota?.startTime).isEqualTo(TEST_START_TIME)
        assertThat(oldQuota?.lastFulfillmentTime).isEqualTo(TEST_LAST_FULFILLMENT_TIME)
        assertThat(getValue(quotaDao.loadAll())).hasSize(1)

        quotaDao.save(Quota(TEST_QUOTA_ID,
            TEST_QUOTA_NAME.reversed(),
            Weekly(Clock.systemDefaultZone()),
            TEST_START_TIME,
            TEST_LAST_FULFILLMENT_TIME
        ))
        val newQuota = getValue(quotaDao.load(TEST_QUOTA_ID))
        assertThat(newQuota?.id).isEqualTo(TEST_QUOTA_ID)
        assertThat(newQuota?.name).isEqualTo(TEST_QUOTA_NAME.reversed())
        assertThat(newQuota?.period).isEqualTo(Weekly(Clock.systemDefaultZone()))
        assertThat(newQuota?.startTime).isEqualTo(TEST_START_TIME)
        assertThat(newQuota?.lastFulfillmentTime).isEqualTo(TEST_LAST_FULFILLMENT_TIME)
        assertThat(getValue(quotaDao.loadAll())).hasSize(1)
    }

    @Test
    fun deleteQuota() {
        val quota = getValue(quotaDao.load(TEST_QUOTA_ID))
        assertThat(quota).isNotNull()
        assertThat(quota?.id).isEqualTo(TEST_QUOTA_ID)

        quotaDao.delete(quota!!)
        val noQuota = getValue(quotaDao.load(TEST_QUOTA_ID))
        assertThat(noQuota).isNull()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
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

    private fun getValue(liveData: LiveData<List<Quota>>): Set<Quota> {
        val quotas = mutableSetOf<Quota>()
        val latch = CountDownLatch(1)
        Handler(Looper.getMainLooper()).post {
            liveData.observeForever { result ->
                quotas.addAll(result)
                latch.countDown()
            }
        }
        latch.await(2, TimeUnit.SECONDS)
        return quotas
    }

}
