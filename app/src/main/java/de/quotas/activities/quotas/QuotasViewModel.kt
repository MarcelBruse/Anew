package de.quotas.activities.quotas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.quotas.models.Quota
import de.quotas.models.QuotasRepository
import kotlinx.coroutines.launch

class QuotasViewModel(private val quotasRepository: QuotasRepository) : ViewModel() {

    val quotas: LiveData<List<Quota>> = quotasRepository.getAllQuotas()

    fun deleteQuota(quota: Quota) = viewModelScope.launch {
        quotasRepository.deleteQuota(quota)
    }

}