package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

interface TimePeriod {

    fun getIntervalIncluding(representative: ZonedDateTime): TimeInterval

    fun currentIntervalIncludes(instant: ZonedDateTime): Boolean

}