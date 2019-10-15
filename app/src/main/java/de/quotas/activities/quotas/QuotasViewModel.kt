package de.quotas.activities.quotas

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import de.quotas.models.Quota
import de.quotas.models.QuotaRepository
import javax.inject.Inject

class QuotasViewModel @Inject constructor(
    quotaRepository: QuotaRepository
) : ViewModel() {

    val quotas : LiveData<List<Quota>> = quotaRepository.getAllQuotas()

}