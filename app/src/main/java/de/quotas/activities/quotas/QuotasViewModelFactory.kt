package de.quotas.activities.quotas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.quotas.models.QuotaRepository
import javax.inject.Inject

class QuotasViewModelFactory @Inject constructor(
    private val quotaRepository: QuotaRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return QuotasViewModel(quotaRepository) as T
    }

}