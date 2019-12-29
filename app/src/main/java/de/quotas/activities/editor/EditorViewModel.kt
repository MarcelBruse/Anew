package de.quotas.activities.editor

import androidx.lifecycle.ViewModel
import de.quotas.google.SingleLiveEvent
import de.quotas.models.Quota
import de.quotas.models.QuotaRepository

class EditorViewModel(private val quotaRepository: QuotaRepository, private val quotaId: Long) : ViewModel() {

    val quotaSaved = SingleLiveEvent<Any>()

    constructor(quotaRepository: QuotaRepository): this(quotaRepository, -1L)

    private fun getQuota(): Quota? = quotaRepository.getQuota(quotaId).value

    fun getQuotaName() = getQuota()?.name ?: ""

    fun saveQuota() {
        quotaSaved.call()
    }

}