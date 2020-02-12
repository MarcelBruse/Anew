package de.anew.models.time

import org.threeten.bp.Clock
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalAdjusters

class Weekly(private val clock: Clock) : TimePeriod {

    constructor(): this(Clock.systemDefaultZone())

    override fun clock() = clock

    override fun currentInterval() = intervalIncluding(ZonedDateTime.now(clock))

    override fun intervalIncluding(representative: ZonedDateTime): TimeInterval {
        val monday = representative.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val startOfWeek = monday.truncatedTo(ChronoUnit.DAYS)
        return WeekInterval(startOfWeek)
    }

    override fun currentIntervalIncludes(instant: ZonedDateTime): Boolean {
        return currentInterval().includes(instant)
    }

    override fun equals(other: Any?): Boolean {
        return other is Weekly && other.clock == clock
    }

    override fun hashCode(): Int {
        return Weekly::class.hashCode() + clock::class.hashCode()
    }

}