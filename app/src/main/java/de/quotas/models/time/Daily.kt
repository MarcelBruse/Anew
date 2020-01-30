package de.quotas.models.time

import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit

class Daily(private val clock: Clock) : TimePeriod {

    constructor(): this(Clock.systemDefaultZone())

    override fun clock() = clock

    override fun currentInterval() = intervalIncluding(ZonedDateTime.now(clock))

    override fun intervalIncluding(representative: ZonedDateTime): TimeInterval {
        val startOfDay = representative.truncatedTo(ChronoUnit.DAYS)
        return DayInterval(startOfDay)
    }

    override fun currentIntervalIncludes(instant: ZonedDateTime) = currentInterval().includes(instant)

    override fun equals(other: Any?): Boolean {
        return other is Daily && other.clock == clock
    }

    override fun hashCode(): Int {
        return Daily::class.hashCode() + clock::class.hashCode()
    }

}