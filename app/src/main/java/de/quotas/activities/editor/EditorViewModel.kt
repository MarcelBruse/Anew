package de.quotas.activities.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.quotas.R
import de.quotas.lifecycle.SingleLiveEvent
import de.quotas.models.QuotaFactory
import de.quotas.models.QuotaValidator
import de.quotas.models.QuotasRepository
import de.quotas.models.time.TimePeriod
import kotlinx.coroutines.launch

class EditorViewModel(private val quotasRepository: QuotasRepository, quotaId: Long) : ViewModel() {

    val quota = if (quotaId > 0) quotasRepository.getQuota(quotaId) else QuotaFactory.newQuota()

    val quotaSavedEvent = SingleLiveEvent<Any>()

    fun saveQuota(quotaName: String, period: TimePeriod) {
        if (validateUserInput(quotaName, period).isEmpty()) {
            quota.value?.let {
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
