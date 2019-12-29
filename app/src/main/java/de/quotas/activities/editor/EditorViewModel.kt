package de.quotas.activities.editor

import androidx.lifecycle.ViewModel
import de.quotas.google.SingleLiveEvent
import de.quotas.models.Quota
import de.quotas.models.QuotasRepository

class EditorViewModel(private val quotasRepository: QuotasRepository, private val quotaId: Long) : ViewModel() {

    val quotaSaved = SingleLiveEvent<Any>()

    constructor(quotasRepository: QuotasRepository): this(quotasRepository, -1L)

    private fun getQuota(): Quota? = quotasRepository.getQuota(quotaId).value

    fun getQuotaName() = getQuota()?.name ?: ""

    fun saveQuota() {
        quotaSaved.call()
    }

}