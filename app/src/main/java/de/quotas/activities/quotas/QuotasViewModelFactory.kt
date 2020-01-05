package de.quotas.activities.quotas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.quotas.models.QuotasRepository

class QuotasViewModelFactory(private val quotasRepository: QuotasRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(QuotasRepository::class.java).newInstance(quotasRepository)
    }

}