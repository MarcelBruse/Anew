package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

abstract class Period {

    abstract fun getIntervalFromRepresentative(representative: ZonedDateTime): Interval

    fun getCurrentInterval() = getIntervalFromRepresentative(ZonedDateTime.now())

}