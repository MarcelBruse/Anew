package de.quotas.models

import androidx.lifecycle.MutableLiveData
import de.quotas.persistency.QuotaDao
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QuotaRepositoryTest {

    @Test
    fun getQuotaFromRepository() {
        val quotaId = 42L
        val quotaDao = mockk<QuotaDao>()
        val liveData = mockk<MutableLiveData<Quota>>()
        every { quotaDao.load(quotaId) } returns liveData
        val quotaRepository = QuotaRepository(quotaDao)
        val quota = quotaRepository.getQuota(quotaId)
        assertThat(quota).isEqualTo(liveData)
    }

}