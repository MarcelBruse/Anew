package de.quotas.models.time

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit

object Daily : Period() {

    override fun getIntervalFromRepresentative(representative: ZonedDateTime): Interval {
        val startOfDay = representative.truncatedTo(ChronoUnit.DAYS)
        return DayInterval(startOfDay)
    }

    override fun equals(other: Any?): Boolean {
        return other is Daily
    }

    override fun hashCode(): Int {
        return Daily::class.hashCode() + 1
    }

}