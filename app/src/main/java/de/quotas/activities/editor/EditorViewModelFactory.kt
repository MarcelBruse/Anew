package de.quotas.activities.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.quotas.models.QuotasRepository

class EditorViewModelFactory(
    private val quotasRepository: QuotasRepository,
    private val quotaId: Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val constructor = modelClass.getConstructor(QuotasRepository::class.java, Long::class.java)
        return constructor.newInstance(quotasRepository, quotaId)
    }

}