package de.quotas.activities.editor

import androidx.lifecycle.ViewModel
import de.quotas.models.Quota
import de.quotas.models.QuotaRepository

class EditorViewModel(private val quotaRepository: QuotaRepository) : ViewModel() {

    fun getQuota(quotaId: Long): Quota? = quotaRepository.getQuota(quotaId).value

}