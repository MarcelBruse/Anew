package de.quotas.activities.quotas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.quotas.models.Quota
import de.quotas.models.QuotasRepository

class QuotasViewModel(private val quotasRepository: QuotasRepository) : ViewModel() {

    val quotasAsLiveData: LiveData<List<Quota>> = quotasRepository.getAllQuotasAsLiveData()

    fun deleteQuota(quota: Quota) = quotasRepository.deleteQuota(quota)

}