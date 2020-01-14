package de.quotas.activities.quotas

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import de.quotas.models.Quota
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Job
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class QuotaAdapterTest {

    private lateinit var quotaAdapter: QuotaAdapter

    private lateinit var quotasViewModel: QuotasViewModel

    private lateinit var quotas: MutableLiveData<List<Quota>>

    @Before
    fun setUp() {
        quotasViewModel = mockk()
        every { quotasViewModel.deleteQuota(any()) } returns Job()
        val quota1 = mockk<Quota>()
        every { quota1.name } returns "Quota1"
        val quota2 = mockk<Quota>()
        every { quota2.name } returns "Quota2"
        quotas = MutableLiveData(listOf(quota1, quota2))
        every { quotasViewModel.quotas } returns quotas
        val quotaItemClickListener = mockk<QuotaItemClickListener>()
        quotaAdapter = QuotaAdapter(quotasViewModel, quotaItemClickListener)
    }

    @Test
    fun adapterDeletesQuota() {
        quotaAdapter.deleteQuotaAt(0)
        quotas.value?.let {
            verify { quotasViewModel.deleteQuota(it[0]) }
        }
    }

    @Test
    fun getNumberOfQuotas() {
        quotas.value?.let {
            assertThat(quotaAdapter.itemCount).isEqualTo(it.size)
        }
    }

    @Test
    fun setTextOnBindViewHolder() {
        val textView = mockk<TextView>()
        every { textView.text = any() } returns Unit
        every { textView.setOnClickListener(any()) }
        val quotaViewHolder = mockk<QuotaAdapter.QuotaViewHolder>()
        every { quotaViewHolder.textView } returns textView
        quotaAdapter.onBindViewHolder(quotaViewHolder, 1)
        verify { textView.text = "Quota2" }
    }

}