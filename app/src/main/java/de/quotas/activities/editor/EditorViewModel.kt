package de.quotas.activities.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.quotas.R
import de.quotas.lifecycle.SingleLiveEvent
import de.quotas.models.QuotaValidator
import de.quotas.models.QuotasRepository
import de.quotas.models.time.TimePeriod
import kotlinx.coroutines.launch

class EditorViewModel(private val quotasRepository: QuotasRepository, quotaId: Long) : ViewModel() {

    constructor(quotasRepository: QuotasRepository): this(quotasRepository, -1L)

    val quotaSavedEvent = SingleLiveEvent<Any>()

    val quota = quotasRepository.getQuota(quotaId)

    fun saveQuota(quotaName: String, period: TimePeriod) {
        if (validateUserInput(quotaName, period).isEmpty()) {
            quota.value?.takeIf { it.isValid() }?.let {
                it.name = quotaName.trim()
                it.period = period
                viewModelScope.launch {
                    quotasRepository.saveQuota(it)
                    quotaSavedEvent.call()
                }
            }
        }
    }

    fun validateUserInput(quotaName: String, period: TimePeriod): List<Int> {
        val quotaNameErrors = QuotaValidator
            .validateQuotaName(quotaName)
            .map { getMessageIdForError(it) }
            .toList()
        val periodErrors = QuotaValidator
            .validatePeriod(period)
            .map { getMessageIdForError(it) }
            .toList()
        return listOf(quotaNameErrors, periodErrors).flatten()
    }

    private fun getMessageIdForError(error: QuotaValidator.Error): Int {
        return when (error) {
            QuotaValidator.Error.QUOTA_NAME_IS_BLANK -> R.string.blank_quota_name
            QuotaValidator.Error.QUOTA_NAME_LENGTH_EXCEEDS_LIMIT -> R.string.quota_name_length
            QuotaValidator.Error.UNDEFINED_PERIOD -> R.string.undefined_period
            else -> R.string.unknown_error
        }
    }

}
