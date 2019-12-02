package de.quotas.models.time

import org.threeten.bp.Period
import org.threeten.bp.ZonedDateTime

class WeekInterval(private val startOfInterval: ZonedDateTime) : TimeInterval {

    private val oneWeek = Period.ofWeeks(1)

    override fun includes(instant: ZonedDateTime): Boolean {
        val fromStartToInstant = Period.between(startOfInterval.toLocalDate(), instant.toLocalDate())
        val difference = oneWeek.minus(fromStartToInstant)
        return !(fromStartToInstant.isNegative || difference.isNegative || difference.isZero)
    }

}