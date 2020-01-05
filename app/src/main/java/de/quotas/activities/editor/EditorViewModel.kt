package de.quotas.activities.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.quotas.google.SingleLiveEvent
import de.quotas.models.QuotasRepository
import kotlinx.coroutines.launch

class EditorViewModel(private val quotasRepository: QuotasRepository, quotaId: Long) : ViewModel() {

    constructor(quotasRepository: QuotasRepository): this(quotasRepository, -1L)

    val quotaSavedEvent = SingleLiveEvent<Any>()

    val quota = quotasRepository.getQuota(quotaId)

    fun saveQuota(quotaName: String) {
        if (quotaName.isBlank()) {
            return
        }
        quota.value?.takeIf { it.isValid() }?.let {
            it.name = quotaName.trim()
            viewModelScope.launch {
                quotasRepository.saveQuota(it)
                quotaSavedEvent.call()
            }
        }
    }

}
