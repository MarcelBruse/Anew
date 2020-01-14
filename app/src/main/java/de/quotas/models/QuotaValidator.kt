package de.quotas.models

import de.quotas.models.QuotaValidator.Error.*
import de.quotas.models.time.TimePeriod
import de.quotas.models.time.UndefinedPeriod
import org.threeten.bp.ZonedDateTime

object QuotaValidator {

    private const val MAX_QUOTA_NAME_LENGTH = 40

    fun validateQuotaName(quotaNameCandidate: String): List<Error> {
        if (quotaNameCandidate.isBlank()) {
            return listOf(QUOTA_NAME_IS_BLANK)
        } else if (quotaNameCandidate.length > MAX_QUOTA_NAME_LENGTH) {
            return listOf(QUOTA_NAME_LENGTH_EXCEEDS_LIMIT)
        }
        return emptyList()
    }

    fun validatePeriod(periodCandidate: TimePeriod): List<Error> {
        return if (periodCandidate is UndefinedPeriod) listOf(UNDEFINED_PERIOD) else emptyList()
    }

    fun validateLastFulfilmentDate(quota: Quota, lastFulfillmentTimeCandidate: ZonedDateTime?): List<Error> {
        lastFulfillmentTimeCandidate?.let {
            if (quota.startTime.isAfter(it)) {
                return listOf(FULFILLED_BEFORE_START)
            }
        }
        return emptyList()
    }

    fun validate(quota: Quota): List<Error> {
        val errors = ArrayList<Error>()
        errors.addAll(validateQuotaName(quota.name))
        errors.addAll(validatePeriod(quota.period))
        errors.addAll(validateLastFulfilmentDate(quota, quota.lastFulfillmentTime))
        return errors
    }

    enum class Error {
        QUOTA_NAME_LENGTH_EXCEEDS_LIMIT,
        QUOTA_NAME_IS_BLANK,
        UNDEFINED_PERIOD,
        FULFILLED_BEFORE_START
    }

}