package de.quotas.models.time

import org.threeten.bp.Period
import org.threeten.bp.ZonedDateTime

class WeekInterval(private val startOfWeek: ZonedDateTime) : TimeInterval {

    private val oneWeek = Period.ofWeeks(1)

    override fun start() = startOfWeek

    override fun next() = WeekInterval(startOfWeek + oneWeek)

    override fun includes(instant: ZonedDateTime): Boolean {
        val fromStartToInstant = Period.between(startOfWeek.toLocalDate(), instant.toLocalDate())
        val difference = oneWeek.minus(fromStartToInstant)
        return !(fromStartToInstant.isNegative || difference.isNegative || difference.isZero)
    }

}