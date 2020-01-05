package de.quotas.activities.quotas

import androidx.recyclerview.widget.RecyclerView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class QuotaTouchHelperTest {

    private lateinit var quotaAdapter: QuotaAdapter

    @Before
    fun setUp() {
        quotaAdapter = mockk()
        every { quotaAdapter.deleteQuotaAt(any()) } returns Unit
    }

    @Test
    fun onMoveIsDisabled() {
        val quotaTouchHelper = QuotaTouchHelper(quotaAdapter)
        val recyclerView = mockk<RecyclerView>()
        val viewHolder = mockk<RecyclerView.ViewHolder>()
        assertThat(quotaTouchHelper.onMove(recyclerView, viewHolder, viewHolder)).isFalse()
    }

    @Test
    fun deletesQuotaOnSwiped() {
        val quotaTouchHelper = QuotaTouchHelper(quotaAdapter)
        val viewHolder = mockk<RecyclerView.ViewHolder>()
        val adapterPosition = 4
        every { viewHolder.adapterPosition } returns adapterPosition
        quotaTouchHelper.onSwiped(viewHolder, adapterPosition)
        verify { quotaAdapter.deleteQuotaAt(adapterPosition) }
    }

}