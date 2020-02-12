package de.anew.models.time

import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime

interface TimePeriod {

    fun clock(): Clock

    fun currentInterval(): TimeInterval

    fun intervalIncluding(representative: ZonedDateTime): TimeInterval

    fun currentIntervalIncludes(instant: ZonedDateTime): Boolean

}