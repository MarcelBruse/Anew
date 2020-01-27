package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

class DayInterval(private val startOfDay: ZonedDateTime) : TimeInterval {

    override fun startsAt() = startOfDay

    override fun endsBefore() = next().startOfDay

    override fun next() = DayInterval(startOfDay.plusDays(1L))

    override fun includes(instant: ZonedDateTime) = !startOfDay.isAfter(instant) && next().startsAt().isAfter(instant)

}