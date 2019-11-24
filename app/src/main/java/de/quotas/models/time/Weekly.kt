package de.quotas.models.time

import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalAdjusters

object Weekly : Period() {

    override fun getIntervalFromRepresentative(representative: ZonedDateTime): Interval {
        val monday = representative.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val startOfWeek = monday.truncatedTo(ChronoUnit.DAYS)
        return WeekInterval(startOfWeek)
    }

    override fun equals(other: Any?): Boolean {
        return other is Weekly
    }

    override fun hashCode(): Int {
        return Weekly::class.hashCode() + 1
    }

}