package de.quotas.activities.quotas

import androidx.lifecycle.LiveData
import de.quotas.models.Quota
import de.quotas.models.QuotasRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QuotasViewModelFactoryTest {

    @Test
    fun viewModelCreation() {
        val quotasRepository = mockk<QuotasRepository>()
        val liveData = mockk<LiveData<List<Quota>>>()
        every { quotasRepository.getAllQuotas() } returns liveData
        val quotasViewModel = QuotasViewModelFactory(quotasRepository).create(QuotasViewModel::class.java)
        assertThat(quotasViewModel).isNotNull
        verify { quotasRepository.getAllQuotas() }
    }

}