package de.quotas.activities.quotas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.quotas.models.QuotaRepository

class QuotasViewModelFactory constructor(
    private val quotaRepository: QuotaRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return QuotasViewModel(quotaRepository) as T
    }

}