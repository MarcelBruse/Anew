package de.quotas.models

import androidx.lifecycle.MutableLiveData
import de.quotas.persistency.QuotaDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QuotasRepositoryTest {

    @Test
    fun getQuota() {
        val quotaDao = mockk<QuotaDao>()
        val liveData = MutableLiveData<Quota>()
        val quotaId = 42L
        every { quotaDao.load(quotaId) } returns liveData
        val quotaRepository = QuotasRepository(quotaDao)
        val quota = quotaRepository.getQuota(quotaId)
        assertThat(quota).isEqualTo(liveData)
    }

    @Test
    fun getAllQuotas() {
        val quotaDao = mockk<QuotaDao>()
        val liveData = MutableLiveData<List<Quota>>()
        every { quotaDao.loadAll() } returns liveData
        val quotasRepository = QuotasRepository(quotaDao)
        val quotas = quotasRepository.getAllQuotas()
        assertThat(quotas).isEqualTo(liveData)
    }

    @Test
    fun saveQuota() {
        val quotaDao = mockk<QuotaDao>()
        val quotaToBeSaved = mockk<Quota>()
        every { quotaDao.save(quotaToBeSaved) } returns Unit
        val quotaRepository = QuotasRepository(quotaDao)
        runBlocking {
            quotaRepository.saveQuota(quotaToBeSaved)
        }
        verify { quotaDao.save(quotaToBeSaved) }
    }

    @Test
    fun deleteQuota() {
        val quotaDao = mockk<QuotaDao>()
        val quotaToBeDeleted = mockk<Quota>()
        every { quotaDao.delete(quotaToBeDeleted) } returns Unit
        val quotaRepository = QuotasRepository(quotaDao)
        runBlocking {
            quotaRepository.deleteQuota(quotaToBeDeleted)
        }
        verify { quotaDao.delete(quotaToBeDeleted) }
    }

}