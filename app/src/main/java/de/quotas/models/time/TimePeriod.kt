package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

interface TimePeriod {

    fun currentInterval(): TimeInterval

    fun intervalIncluding(representative: ZonedDateTime): TimeInterval

    fun currentIntervalIncludes(instant: ZonedDateTime): Boolean

}