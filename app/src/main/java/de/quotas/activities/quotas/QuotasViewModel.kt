package de.quotas.activities.quotas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.quotas.models.Quota
import de.quotas.models.QuotaRepository

class QuotasViewModel(private val quotaRepository: QuotaRepository) : ViewModel() {

    val quotas: LiveData<List<Quota>> = quotaRepository.getAllQuotas()

    fun deleteQuota(quota: Quota) = quotaRepository.deleteQuota(quota)

}