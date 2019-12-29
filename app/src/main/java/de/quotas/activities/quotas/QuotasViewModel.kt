package de.quotas.activities.quotas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.quotas.models.Quota
import de.quotas.models.QuotasRepository

class QuotasViewModel(private val quotasRepository: QuotasRepository) : ViewModel() {

    val quotas: LiveData<List<Quota>> = quotasRepository.getAllQuotas()

    fun deleteQuota(quota: Quota) = quotasRepository.deleteQuota(quota)

}