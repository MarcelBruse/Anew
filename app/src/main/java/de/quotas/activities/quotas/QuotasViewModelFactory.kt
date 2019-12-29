package de.quotas.activities.quotas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.quotas.models.QuotasRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotasViewModelFactory @Inject constructor(private val quotasRepository: QuotasRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(QuotasRepository::class.java).newInstance(quotasRepository)
    }

}