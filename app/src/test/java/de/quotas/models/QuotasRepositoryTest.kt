package de.quotas.models

import androidx.lifecycle.MutableLiveData
import de.quotas.persistency.QuotaDao
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QuotasRepositoryTest {

    @Test
    fun getQuotaFromRepository() {
        val quotaId = 42L
        val quota = mockk<Quota>()
        val quotaDao = mockk<QuotaDao>()
        every { quotaDao.load(quotaId) } returns quota
        val quotaRepository = QuotasRepository(quotaDao)
        val queriedQuota = quotaRepository.getQuota(quotaId)
        assertThat(queriedQuota).isEqualTo(quota)
    }

    @Test
    fun getQuotaFromRepositoryAsLiveData() {
        val quotaId = 42L
        val quotaDao = mockk<QuotaDao>()
        val liveData = mockk<MutableLiveData<Quota>>()
        every { quotaDao.loadAsLiveData(quotaId) } returns liveData
        val quotaRepository = QuotasRepository(quotaDao)
        val quota = quotaRepository.getQuotaAsLiveData(quotaId)
        assertThat(quota).isEqualTo(liveData)
    }

}