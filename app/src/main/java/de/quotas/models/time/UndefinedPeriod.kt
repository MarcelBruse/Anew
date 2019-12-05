package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

object UndefinedPeriod : TimePeriod {

    override fun getIntervalIncluding(representative: ZonedDateTime): TimeInterval {
        throw UnsupportedOperationException()
    }

    override fun currentIntervalIncludes(instant: ZonedDateTime): Boolean {
        throw UnsupportedOperationException()
    }

}