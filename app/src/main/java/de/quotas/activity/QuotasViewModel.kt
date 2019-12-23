package de.quotas.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.quotas.models.Quota
import de.quotas.models.QuotaRepository

class QuotasViewModel(quotaRepository: QuotaRepository) : ViewModel() {

    val quotas : LiveData<List<Quota>> = quotaRepository.getAllQuotas()

}