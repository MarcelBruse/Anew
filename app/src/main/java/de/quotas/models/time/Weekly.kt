package de.quotas.models.time

import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalAdjusters

object Weekly : TimePeriod {

    override fun getIntervalContaining(representative: ZonedDateTime): TimeInterval {
        val monday = representative.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val startOfWeek = monday.truncatedTo(ChronoUnit.DAYS)
        return WeekInterval(startOfWeek)
    }

    override fun currentIntervalIncludes(instant: ZonedDateTime) =
        getIntervalContaining(ZonedDateTime.now()).includes(instant)

}