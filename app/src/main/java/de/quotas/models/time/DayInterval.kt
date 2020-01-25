package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

class DayInterval(private val startOfDay: ZonedDateTime) : TimeInterval {

    override fun start() = startOfDay

    override fun next() = DayInterval(startOfDay.plusDays(1L))

    override fun includes(instant: ZonedDateTime) = !startOfDay.isAfter(instant) && next().start().isAfter(instant)

}