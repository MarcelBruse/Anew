package de.quotas.activities.editor

import androidx.lifecycle.LiveData
import de.quotas.models.Quota
import de.quotas.models.QuotasRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EditorViewModelFactoryTest {

    @Test
    fun viewModelCreation() {
        val quotasRepository = mockk<QuotasRepository>()
        val liveData = mockk<LiveData<Quota>>()
        val quotaId = 42L
        every { quotasRepository.getQuota(quotaId) } returns liveData
        val editorViewModelFactory = EditorViewModelFactory(quotasRepository, quotaId)
        assertThat(editorViewModelFactory).isNotNull
        val editorViewModel = editorViewModelFactory.create(EditorViewModel::class.java)
        assertThat(editorViewModel).isNotNull
        verify { quotasRepository.getQuota(quotaId) }
    }

}