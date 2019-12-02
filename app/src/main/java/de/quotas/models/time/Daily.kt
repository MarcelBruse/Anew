package de.quotas.models.time

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit

object Daily : TimePeriod {

    override fun getIntervalContaining(representative: ZonedDateTime): TimeInterval {
        val startOfDay = representative.truncatedTo(ChronoUnit.DAYS)
        return DayInterval(startOfDay)
    }

    override fun currentIntervalIncludes(instant: ZonedDateTime) =
        getIntervalContaining(ZonedDateTime.now()).includes(instant)

}