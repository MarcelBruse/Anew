package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

object UndefinedPeriod : TimePeriod {

    override fun currentInterval(): TimeInterval {
        throw UnsupportedOperationException()
    }

    override fun intervalIncluding(representative: ZonedDateTime): TimeInterval {
        throw UnsupportedOperationException()
    }

    override fun currentIntervalIncludes(instant: ZonedDateTime): Boolean {
        throw UnsupportedOperationException()
    }

}