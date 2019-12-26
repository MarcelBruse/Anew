package de.quotas.activities.quotas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.quotas.models.QuotaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotasViewModelFactory @Inject constructor(private val quotaRepository: QuotaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(QuotaRepository::class.java).newInstance(quotaRepository)
    }

}