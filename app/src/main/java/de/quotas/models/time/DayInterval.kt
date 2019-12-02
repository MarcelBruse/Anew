package de.quotas.models.time

import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

class DayInterval(private val startOfInterval: ZonedDateTime) : TimeInterval {

    private val oneDay = Duration.ofDays(1)

    override fun includes(instant: ZonedDateTime): Boolean {
        val duration = Duration.between(startOfInterval, instant)
        return Duration.ZERO <= duration && duration < oneDay
    }

}